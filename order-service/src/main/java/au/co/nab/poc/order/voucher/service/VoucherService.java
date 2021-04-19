package au.co.nab.poc.order.voucher.service;

import au.co.nab.poc.order.voucher.dto.VoucherDto;

import java.util.List;

/**
 * @author andy - tuantda.uit@gmail.com
 */
public interface VoucherService {
    String createVoucher(VoucherDto dto);
    List<VoucherDto> findAllVouchers();
}
