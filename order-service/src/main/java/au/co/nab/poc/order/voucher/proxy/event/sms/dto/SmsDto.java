package au.co.nab.poc.order.voucher.proxy.event.sms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class SmsDto implements Serializable {
    private String targetPhone;
    private String message;
}
