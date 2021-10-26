package jbcourse.couponSystemPhase3.rest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jbcourse.couponSystemPhase3.authentication.IAuthenticationFacade;
import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.exceptions.CouponDateException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.exceptions.PermissionException;
import jbcourse.couponSystemPhase3.services.AdminService;
import jbcourse.couponSystemPhase3.services.CompanyService;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;

@CrossOrigin(origins = "${cors.origin}")
@RestController
@RequestMapping("sec/company")
public class CompanyWebService {

	@Autowired
	CompanyService companyService;
	
	@Autowired
	AdminService adminService; //when deleting a coupon, it is necessary to delete it from customers first.

	@Autowired
	IAuthenticationFacade authenticationFacade;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
	public ResponseEntity handle() {
		return new ResponseEntity(HttpStatus.OK);
	}

	// This method will be used throughout the controller to retrieve company id and
	// use it in the calls to service.
	private long currentCompanyId() {
		Authentication authentication = authenticationFacade.getAuthentication();
		return companyService.getCompanyByName(authentication.getName()).getId();

	}

	@RequestMapping(path = "details")
	public Company getCompanyDetails() throws ObjectNotFoundException {

		return companyService.getCompanyById(currentCompanyId());
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Coupon> getAllCoupons() {
		return companyService.getCompanyCoupons(currentCompanyId());
	}

	@PostMapping(path = "createcoupon")
	public Coupon createNewCoupon(@RequestBody Coupon coupon) throws CouponDateException {
		companyService.createCoupon(coupon, currentCompanyId());
		return coupon;
	}

	@RequestMapping(path = "coupon/{couponId}")
	public Coupon getCouponById(@PathVariable long couponId) throws ObjectNotFoundException, PermissionException {
		return companyService.getCouponById(couponId, currentCompanyId());
	}

	@PutMapping(path = "updatecoupon/{couponId}")
	public void updateCoupon(@PathVariable long couponId, @RequestBody Coupon coupon)
			throws PermissionException, ObjectNotFoundException, CouponDateException {
		coupon.setId(couponId);

		companyService.updateCoupon(coupon, currentCompanyId());
	}

	@DeleteMapping(path = "removecoupon/{couponId}")
	public void removeCoupon(@PathVariable long couponId) throws ObjectNotFoundException, PermissionException {
		adminService.removeCouponFromCustomers(couponId);
		companyService.removeCoupon(couponId, currentCompanyId());
	}

	@RequestMapping(path = "byPrice/{price}")
	public List<Coupon> getCouponsLessThanPrice(@PathVariable double price) {
		return companyService.getCouponsLessThanPrice(price, currentCompanyId());
	}

	@RequestMapping(path = "beforeDate/{endDate}")
	public List<Coupon> getCouponsBeforeDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		return companyService.getCouponsBeforeDate(endDate, currentCompanyId());
	}

	@RequestMapping(path = "category/{category}")
	public List<Coupon> getCouponsByCategory(@PathVariable CouponCategory category) {
		return companyService.getCouponsByCategory(category, currentCompanyId());
	}

	@RequestMapping(path = "logout")
	public void logout() {
		authenticationFacade.getAuthentication().setAuthenticated(false);
	}

}
