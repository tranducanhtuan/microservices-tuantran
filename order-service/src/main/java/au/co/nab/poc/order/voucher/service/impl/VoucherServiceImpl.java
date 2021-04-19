package au.co.nab.poc.order.voucher.service.impl;


import au.co.nab.poc.order.constant.CustomHttpHeaders;
import au.co.nab.poc.order.voucher.dto.VoucherDto;
import au.co.nab.poc.order.voucher.entity.VoucherJpo;
import au.co.nab.poc.order.voucher.proxy.event.sms.SmsProducer;
import au.co.nab.poc.order.voucher.proxy.event.sms.dto.SmsDto;
import au.co.nab.poc.order.voucher.proxy.rest.proxy.VoucherThirdPartyProxy;
import au.co.nab.poc.order.voucher.repository.VoucherRepository;
import au.co.nab.poc.order.voucher.service.VoucherService;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@Service
public class VoucherServiceImpl implements VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherThirdPartyProxy voucherThirdPartyProxy;
    @Autowired
    private SmsProducer smsProducer;

    @Value("${voucherThirdParty.waitTimeout}")
    private long voucherThirdPartyWaitTimeout;

    public String createVoucher(VoucherDto dto) {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> voucherResult = executor.submit(() -> callCreateVoucher(dto));
            return voucherResult.get(voucherThirdPartyWaitTimeout, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
            return "500: System Error";
        } catch (TimeoutException e) {
            logger.info("--Creating voucher has been taken more than 30 second, will send voucher via SMS.");
            return "You will receive voucher via SMS later.";
        }
    }

    private String callCreateVoucher(VoucherDto dto) {
        String voucherCode = voucherThirdPartyProxy.getVoucher();
        if (StringUtils.isEmpty(voucherCode)) {
            return "Receive voucher failed.";
        }

        VoucherJpo voucherJpo = new VoucherJpo();
        BeanUtils.copyProperties(dto, voucherJpo);
        voucherJpo.setCode(voucherCode);
        voucherJpo.setCreatedAt(new Date());

        voucherRepository.save(voucherJpo);

        SmsDto smsDto = new SmsDto(
                "0909492488", // Login User Phone
                "Your voucher for " + voucherJpo.getPhone() + " is " + voucherJpo.getCode()
        );

        logger.info("--Request to send SMS to " + smsDto.getTargetPhone() + " with message:");
        logger.info("--" + smsDto.getMessage());
        smsProducer.getSmsSource().output().send(MessageBuilder.withPayload(smsDto).build());

        return voucherJpo.getCode();
    }

    public List<VoucherDto> findAllVouchers() {
        String phone = MDC.get(CustomHttpHeaders.PHONE);
        logger.info("phone="+ MDC.get(CustomHttpHeaders.PHONE));
        List<VoucherJpo> voucherJpoList = voucherRepository.findByPhoneEqualsOrderByCreatedAtDesc(phone);

        return voucherJpoList.stream().map(voucherJpo -> {
            VoucherDto voucherDto = new VoucherDto();
            BeanUtils.copyProperties(voucherJpo, voucherDto);
            return voucherDto;
        }).collect(Collectors.toList());
    }
}
