package au.co.nab.poc.order.voucher.proxy.event.sms;

import au.co.nab.poc.order.voucher.proxy.event.sms.source.SmsSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.stream.annotation.EnableBinding;

@AllArgsConstructor //Must have this as we need to inject "smsSource" bean each time injecting "smsProducer" bean.
@Getter
@EnableBinding(SmsSource.class)
public class SmsProducer {
    private SmsSource smsSource;
}
