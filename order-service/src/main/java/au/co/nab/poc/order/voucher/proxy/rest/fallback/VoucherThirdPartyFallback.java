package au.co.nab.poc.order.voucher.proxy.rest.fallback;

import au.co.nab.poc.order.voucher.proxy.rest.client.VoucherThirdPartyClient;
import org.springframework.stereotype.Component;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@Component
public class VoucherThirdPartyFallback implements VoucherThirdPartyClient {

    @Override
    public String getVoucher() {
        return null;
    }

}