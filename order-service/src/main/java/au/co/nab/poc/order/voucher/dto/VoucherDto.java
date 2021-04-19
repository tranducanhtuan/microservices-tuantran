package au.co.nab.poc.order.voucher.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import lombok.Data;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VoucherDto {

    private Integer voucherId;
    private String phone;
    private String code;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

}
