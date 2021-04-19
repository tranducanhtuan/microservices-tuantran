package au.co.thirdparty.poc.voucher.controller;


import au.co.thirdparty.poc.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@RestController
@RequestMapping(path = "voucher", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PostMapping
    public String createVoucher() throws InterruptedException {
        return voucherService.createVoucher();
    }

}
