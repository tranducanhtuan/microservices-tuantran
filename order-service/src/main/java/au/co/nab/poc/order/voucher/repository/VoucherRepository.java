package au.co.nab.poc.order.voucher.repository;

import au.co.nab.poc.order.voucher.entity.VoucherJpo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@Repository
public interface VoucherRepository extends JpaRepository<VoucherJpo, Integer> {
    List<VoucherJpo> findByPhoneEqualsOrderByCreatedAtDesc(String phone);
}
