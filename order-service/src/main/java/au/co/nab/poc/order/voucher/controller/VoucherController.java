package au.co.nab.poc.order.voucher.controller;

import au.co.nab.poc.order.voucher.dto.VoucherDto;
import au.co.nab.poc.order.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@RestController
@RequestMapping(path = "voucher", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PostMapping
    public String createVoucher(@RequestBody VoucherDto dto) {
        return voucherService.createVoucher(dto);
    }

    @GetMapping
    public List<VoucherDto> getAllVouchers() {
        return voucherService.findAllVouchers();
    }

}
