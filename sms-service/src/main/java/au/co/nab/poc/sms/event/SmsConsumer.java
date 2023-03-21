package au.co.nab.poc.sms.event;

import au.co.nab.poc.sms.dto.SmsDto;
import au.co.nab.poc.sms.event.sink.SmsSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@EnableBinding(SmsSink.class)
@Slf4j
public class SmsConsumer {

    @StreamListener(target = SmsSink.INPUT)
    public void consume(@Payload SmsDto smsDto) {
        log.info("--SMS has been sent to " + smsDto.getTargetPhone() + " with message:");
        log.info("--" + smsDto.getMessage());
    }

}
