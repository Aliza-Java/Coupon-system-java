package jbcourse.couponSystemPhase3.rest;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.exceptions.CouponDateException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.exceptions.PermissionException;
import jbcourse.couponSystemPhase3.services.CompanyService;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;
import jbcourse.couponSystemPhase3.util_classes.User;

@RestController
@RequestMapping("sec/company")
public class CompanyWebService {

	@Autowired
	CompanyService companyService;

	@Autowired
	HttpSession session;


	@RequestMapping(path = "details/{companyId}")
	public Company getCompanyDetails(@PathVariable long companyId) throws ObjectNotFoundException {

		return companyService.getCompanyById(((User) session.getAttribute("user")).getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Coupon> getAllCoupons() {

		return companyService.getAllCoupons(((User) session.getAttribute("user")).getId());
	}

	@PostMapping(path = "createcoupon")
	public Coupon createNewCoupon(@RequestBody Coupon coupon) throws CouponDateException {
		companyService.createCoupon(coupon, ((User) session.getAttribute("user")).getId());
		return coupon;
	}

	@RequestMapping(path = "coupon/{couponId}")
	public Coupon getCouponById(@PathVariable long couponId) throws ObjectNotFoundException, PermissionException {
		return companyService.getCouponById(couponId, ((User) session.getAttribute("user")).getId());
	}

	@PutMapping(path = "updatecoupon/{couponId}")
	public void updateCoupon(@PathVariable long couponId, @RequestBody Coupon coupon)
			throws PermissionException, ObjectNotFoundException, CouponDateException {
		coupon.setId(couponId);

		companyService.updateCoupon(coupon, ((User) session.getAttribute("user")).getId());
	}

	@DeleteMapping(path = "removecoupon/{couponId}")
	public void removeCoupon(@PathVariable long couponId) throws ObjectNotFoundException, PermissionException {
		companyService.removeCoupon(couponId, ((User) session.getAttribute("user")).getId());
	}

	@RequestMapping(path = "byPrice/{price}")
	public List<Coupon> getCouponsLessThanPrice(@PathVariable double price) {
		return companyService.getCouponsLessThanPrice(price, ((User) session.getAttribute("user")).getId());
	}

	@RequestMapping(path = "beforeDate/{endDate}")
	public List<Coupon> getCouponsBeforeDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		return companyService.getCouponsBeforeDate(endDate, ((User) session.getAttribute("user")).getId());
	}

	@RequestMapping(path = "category/{category}")
	public List<Coupon> getCouponsByCategory(@PathVariable CouponCategory category) {
		return companyService.getCouponsByCategory(category, ((User) session.getAttribute("user")).getId());
	}

	public void logout() {
		session.invalidate();
	}

}
