package au.co.nab.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@SpringBootApplication
@EnableEurekaClient
public class SmsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsServiceApplication.class, args);
	}

}
