package jbcourse.couponSystemPhase3.repositories;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

	// When coupon is purchased, amount is decreased
	@Modifying
	@Transactional
	@Query("update Coupon c set c.amount=c.amount-1 where id=?1")
	public void decrementCouponAmount(long id);


	public List<Coupon> findByCompanyIdAndCategoryOrderById(long companyId, CouponCategory category);

	List<Coupon> findAllByCompanyIdAndPriceLessThanEqualOrderById(long companyId, double price);

	List<Coupon> findByEndDateLessThanEqualOrderById(LocalDate endDate);

	List<Coupon> findByEndDateBeforeOrderById(LocalDate endDate);

	// this is for the scheduled task which deletes expired coupons.
	List<Coupon> findByCompanyIdAndEndDateLessThanEqualOrderById(long companyId, LocalDate endDate);

	// Used by CompanyServiceImpl to see company's coupons
	//@Query("select c from Coupon c where c.companyId=?1")
	List<Coupon> findByCompanyId(long companyId);
	


}
