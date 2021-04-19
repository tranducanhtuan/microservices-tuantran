package au.co.thirdparty.poc.voucher.service.impl;


import au.co.thirdparty.poc.voucher.service.VoucherService;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@Service
public class VoucherServiceImpl implements VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);
    private static final List<Integer> sleepListInSeconds = Arrays.asList(3, 40); // seconds

    public String createVoucher() throws InterruptedException {
        Random rand = new Random();
        int sleepInSeconds = sleepListInSeconds.get(rand.nextInt(sleepListInSeconds.size()));

        if (sleepInSeconds == 3) {
            logger.info("--Simulating happy case  : response in " + sleepInSeconds + " seconds..");
        } else {
            logger.info("--Simulating unhappy case: response in " + sleepInSeconds + " seconds..");
        }
        Thread.sleep(sleepInSeconds * 1000);

        return UUID.randomUUID().toString();
    }

}
