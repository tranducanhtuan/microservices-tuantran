package au.co.nab.poc.order.voucher.proxy.event.sms.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author andy - tuantda.uit@gmail.com
 */
public interface SmsSource {

	/**
	 * Name of the output channel.
	 */
	String OUTPUT = "smsOutput";

	/**
	 * @return output channel
	 */
	@Output(SmsSource.OUTPUT)
	MessageChannel output();

}
