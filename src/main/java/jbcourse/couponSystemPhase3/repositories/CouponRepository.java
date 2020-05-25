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

	//When coupon is purchased, amount is decreased
	@Modifying
	@Transactional
	@Query("update Coupon c set c.amount=c.amount-1 where id=?1")
	public void decrementCouponAmount(long id);

	public List<Coupon> findByCompanyIdAndCategoryOrderByIdAsc(Long id, CouponCategory category);
	
	List<Coupon> findAllByCompanyIdAndPriceLessThanEqualOrderByIdAsc(long companyId, double price);
	
	List<Coupon> findByCompanyIdAndEndDateLessThanEqualOrderByIdAsc(long companyId, LocalDate endDate);
	
	//this is for the scheduled task which deletes expired coupons.
	List<Coupon> findByEndDateBeforeOrderByIdAsc(LocalDate endDate);
	
	//Used by CompanyServiceImpl to see company's coupons
	List<Coupon> findAllByCompanyIdOrderByIdAsc(long id);
	
	//used by CustomerServiceImpl to see available coupons
	List<Coupon> findAllByOrderByIdAsc();
	
}
