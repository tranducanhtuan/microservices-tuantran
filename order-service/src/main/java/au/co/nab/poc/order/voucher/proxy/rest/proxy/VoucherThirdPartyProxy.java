package au.co.nab.poc.order.voucher.proxy.rest.proxy;

import au.co.nab.poc.order.voucher.proxy.rest.client.VoucherThirdPartyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@Component
public class VoucherThirdPartyProxy {

    @Autowired
    private VoucherThirdPartyClient voucherThirdPartyClient;

    public String getVoucher(){
        return voucherThirdPartyClient.getVoucher();
    }

}
