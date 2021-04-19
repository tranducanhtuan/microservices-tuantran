package au.co.nab.poc.sms.event.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author andy - tuantda.uit@gmail.com
 */
public interface SmsSink {

	/**
	 * Input channel name.
	 */
	String INPUT = "smsInput";

	/**
	 * @return input channel.
	 */
	@Input(INPUT)
	SubscribableChannel input();

}
