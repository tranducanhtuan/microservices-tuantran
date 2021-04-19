package au.co.nab.poc.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author tuantda.uit@gmail.com (Andy)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {

    private Integer companyId;
    private String companyName;
    private Integer userId;
    private String userName;
    private String phone;

}
