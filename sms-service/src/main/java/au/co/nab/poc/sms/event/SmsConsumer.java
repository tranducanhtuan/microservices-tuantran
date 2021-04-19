package au.co.nab.poc.sms.event;

import au.co.nab.poc.sms.dto.SmsDto;
import au.co.nab.poc.sms.event.sink.SmsSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@EnableBinding(SmsSink.class)
public class SmsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SmsConsumer.class);

    @StreamListener(target = SmsSink.INPUT)
    public void consume(@Payload SmsDto smsDto) {
        logger.info("--SMS has been sent to " + smsDto.getTargetPhone() + " with message:");
        logger.info("--" + smsDto.getMessage());
    }

}
