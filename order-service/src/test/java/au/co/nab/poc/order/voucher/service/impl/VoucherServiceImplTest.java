package au.co.nab.poc.order.voucher.service.impl;

import au.co.nab.poc.order.voucher.dto.VoucherDto;
import au.co.nab.poc.order.voucher.entity.VoucherJpo;
import au.co.nab.poc.order.voucher.proxy.event.sms.SmsProducer;
import au.co.nab.poc.order.voucher.proxy.event.sms.source.SmsSource;
import au.co.nab.poc.order.voucher.proxy.rest.proxy.VoucherThirdPartyProxy;
import au.co.nab.poc.order.voucher.repository.VoucherRepository;
import au.co.nab.poc.order.voucher.service.VoucherService;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.StringUtil;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.util.ReflectionTestUtils;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author andy - tuantda.uit@gmail.com
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({})
@RunWith(MockitoJUnitRunner.class)
public class VoucherServiceImplTest {

    @InjectMocks
    private VoucherServiceImpl voucherServiceImpl;

    @Mock
    private VoucherRepository voucherRepository;
    @Mock
    private VoucherThirdPartyProxy voucherThirdPartyProxy;
    @Mock
    private SmsProducer smsProducer;

    @Before
    public void setMockOutput() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateVoucher_receiveFailed() {
        VoucherDto dto = new VoucherDto();
        dto.setPhone("0988123456");

        ReflectionTestUtils.setField(voucherServiceImpl, "voucherThirdPartyWaitTimeout", 10);
        when(voucherThirdPartyProxy.getVoucher()).thenReturn(null);

        String result = voucherServiceImpl.createVoucher(dto);
        assertEquals("Receive voucher failed.", result);
    }

    @Test
    public void testCreateVoucher_timeout() {
        VoucherDto dto = new VoucherDto();
        dto.setPhone("0988123456");

        ReflectionTestUtils.setField(voucherServiceImpl, "voucherThirdPartyWaitTimeout", 0);
        when(voucherThirdPartyProxy.getVoucher()).thenReturn(null);

        String result = voucherServiceImpl.createVoucher(dto);
        assertEquals("You will receive voucher via SMS later.", result);
    }

    @Test
    public void testCreateVoucher_success() {
        VoucherDto dto = new VoucherDto();
        dto.setPhone("0988123456");

        ReflectionTestUtils.setField(voucherServiceImpl, "voucherThirdPartyWaitTimeout", 10);
        when(voucherThirdPartyProxy.getVoucher()).thenReturn("voucher code ABC");
        when(voucherRepository.save(any(VoucherJpo.class))).thenReturn(new VoucherJpo());
        SmsSource smsSource = new SmsSource() {
            @Override
            public MessageChannel output() {
                return new MessageChannel() {
                    @Override
                    public boolean send(Message<?> message, long l) {
                        return true;
                    }
                };
            }
        };
        when(smsProducer.getSmsSource()).thenReturn(smsSource);

        String result = voucherServiceImpl.createVoucher(dto);
        assertEquals("voucher code ABC", result);
    }

    @Test
    public void testCreateVoucher_findAllVouchers() {
        VoucherDto dto = new VoucherDto();
        dto.setPhone("0988123456");

        VoucherJpo jpo = new VoucherJpo();
        jpo.setCode("voucher code ABC");
        List<VoucherJpo> jpoList = Arrays.asList(jpo);
        when(voucherRepository.findByPhoneEqualsOrderByCreatedAtDesc(any())).thenReturn(jpoList);

        List<VoucherDto> dtoList = voucherServiceImpl.findAllVouchers();
        assertEquals(jpoList.size(), dtoList.size());
        assertEquals(jpoList.size(), dtoList.size());
        assertEquals("voucher code ABC", dtoList.get(0).getCode());
    }

}