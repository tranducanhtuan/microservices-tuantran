package au.co.nab.poc.order.voucher.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@Data
@Entity
@Table(name = "voucher")
public class VoucherJpo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "voucher_id", unique = true, nullable = false)
    private Integer voucherId;

    @Column(name = "phone", length = 11, nullable = false)
    private String phone;

    @Column(name = "code", length = 36, nullable = false)
    private String code;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

}
