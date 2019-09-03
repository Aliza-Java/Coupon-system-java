package jbcourse.couponSystemPhase3.services;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.exceptions.CouponDateException;
import jbcourse.couponSystemPhase3.exceptions.LoginException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.exceptions.PermissionException;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;

public interface CompanyService {

	public Coupon createCoupon(@Valid Coupon coupon, long companyId) throws CouponDateException;
	
	public void updateCoupon(@NotNull(message="Updated coupon cannot be null. ") @Valid Coupon coupon, long companyId) throws PermissionException, ObjectNotFoundException, CouponDateException;

	public void removeCoupon (long couponId, long companyId) throws ObjectNotFoundException, PermissionException;
		
	public Coupon getCouponById(long couponId, long companyId) throws ObjectNotFoundException, PermissionException;

	public List<Coupon> getAllCoupons(long companyId);
	
	public List<Coupon> getCouponsLessThanPrice(@Positive double price, long companyId);
	
	public List<Coupon> getCouponsBeforeDate(LocalDate date, long companyId);
	
	public List<Coupon> getCouponsByCategory(CouponCategory category, long companyId);

	public Company login(@NotNull @NotBlank String username, @NotNull @NotBlank String password) throws LoginException;

	public Company getCompanyById(long id);
		
}
