package au.co.nab.poc.order.voucher.proxy.rest.client;

import au.co.nab.poc.order.voucher.proxy.rest.fallback.VoucherThirdPartyFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@FeignClient(name = "voucherThirdParty", url = "${voucherThirdParty.url}", fallback = VoucherThirdPartyFallback.class)
public interface VoucherThirdPartyClient {

    @PostMapping("/voucher")
    String getVoucher();

}