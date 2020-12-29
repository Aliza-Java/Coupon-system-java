package jbcourse.couponSystemPhase3.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Coupon;
import jbcourse.couponSystemPhase3.exceptions.CouponDateException;
import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;
import jbcourse.couponSystemPhase3.exceptions.PermissionException;
import jbcourse.couponSystemPhase3.repositories.CompanyRepository;
import jbcourse.couponSystemPhase3.repositories.CouponRepository;
import jbcourse.couponSystemPhase3.repositories.CustomerRepository;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;

@Validated
@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Company loadCompanyByUsernameAndPw(String username, String password) throws UsernameNotFoundException {

		Optional<Company> optionalCompany = companyRepository.getCompanyByNameAndPassword(username, password);
		if (!optionalCompany.isPresent()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return optionalCompany.get();
	}

	public Company getCompanyById(long companyId) {
		Optional<Company> optionalCompany = companyRepository.findById(companyId);
		if (!optionalCompany.isPresent()) {
			return null;
		}
		return optionalCompany.get();
	}

//	public UserDetails getCompanyUserDetails(long companyId) throws ObjectNotFoundException {
//		Company company = getCompanyById(companyId);
//		if (company != null) {
//			return new User(company.getName(), company.getPassword(), new ArrayList<>());
//		} else {
//			throw new ObjectNotFoundException("company", companyId);
//		}
//	}

	public Company getCompanyByName(String name) {
		Optional<Company> optionalCompany = companyRepository.findByName(name);
		if (!optionalCompany.isPresent()) {
			return null;
		}
		return optionalCompany.get();
	}

	@Override
	public List<Coupon> getCompanyCoupons(long companyId) {
		return couponRepository.findByCompanyId(companyId);
	}

	@Override
	public Coupon createCoupon(Coupon coupon, long companyId) throws CouponDateException {
		Company company = getCompanyById(companyId); // checking if exists and won't throw exception
		validateCouponDates(coupon.getStartDate(), coupon.getEndDate());
		coupon.setCompany(company);
		couponRepository.save(coupon);
		return coupon; // So that we can have the coupon's id
	}

	@Override
	public Coupon getCouponById(long couponId, long companyId) throws ObjectNotFoundException, PermissionException {
		Coupon coupon = checkIfExists(couponId);
		if (coupon != null) {
			checkCompanyPermission(couponId, companyId);
		}
		return coupon;
	}

	@Override
	public Coupon checkIfExists(long couponId) throws ObjectNotFoundException {
		Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
		if (!optionalCoupon.isPresent()) {
			throw new ObjectNotFoundException("Coupon", couponId);
		}
		return optionalCoupon.get();
	}

	@Override
	public void updateCoupon(Coupon updatedCoupon, long companyId)
			throws PermissionException, ObjectNotFoundException, CouponDateException {

		/*
		 * Incoming coupon does not register company although it is sent from client.
		 * Here we retrieve the coupon from the database to check its company for
		 * permission reasons.
		 */
		checkCompanyPermission(updatedCoupon.getId(), companyId);
		checkIfExists(updatedCoupon.getId());
		validateCouponDates(updatedCoupon.getStartDate(), updatedCoupon.getEndDate());
		couponRepository.save(updatedCoupon);

	}

	@Override
	@Transactional
	public void removeCoupon(long couponId, long companyId) throws ObjectNotFoundException, PermissionException {
		try {
			checkIfExists(couponId);
			checkCompanyPermission(couponId, companyId);
			
			//need to remove from customers before deleting (otherwise get SQL exception)
			
			
			couponRepository.deleteById(couponId);
		} catch (EmptyResultDataAccessException | EntityNotFoundException e) {
			throw new ObjectNotFoundException("Coupon", couponId);
		}
	}

	@Override
	public List<Coupon> getCouponsLessThanPrice(double price, long companyId) {
		return couponRepository.findAllByCompanyIdAndPriceLessThanEqualOrderById(companyId, price);
	}

	@Override
	public List<Coupon> getCouponsBeforeDate(LocalDate endDate, long companyId) {
		return couponRepository.findByCompanyIdAndEndDateLessThanEqualOrderById(companyId, endDate);
	}

	@Override
	public List<Coupon> getCouponsByCategory(CouponCategory category, long companyId) {
		return couponRepository.findByCompanyIdAndCategoryOrderById(companyId, category);
	}

	private boolean checkCompanyPermission(long couponId, long loggedCompanyId)
			throws PermissionException, ObjectNotFoundException {
		// Verifying that company actually owns this coupon and can touch only its
		// coupons
		Coupon coupon = checkIfExists(couponId);
		if (coupon.getCompany().getId() == loggedCompanyId) {
			return true;
		}
		throw new PermissionException("This coupon does not belong to your company.");
	}

	// A helper method for updating coupon and creating new one.
	private void validateCouponDates(LocalDate startDate, LocalDate endDate) throws CouponDateException {
		if (startDate.isAfter(endDate)) {
			throw new CouponDateException("The coupon's expiry date must not be before its start date.");
		}

	}

//	// sort through a large batch of coupons and return only those that belong to a
//	// specified company
//	private List<Coupon> onlyCompanyCoupons(long companyId, List<Coupon> couponsToSort) {
//		List<Coupon> neatCoupons = new ArrayList<Coupon>();
//		for (Coupon c : couponsToSort) {
//			if (foundInCompanysCoupons(c.getId(), companyId)) {
//				neatCoupons.add(c);
//			}
//		}
//		return neatCoupons;
//	}

	// check if a certain coupon belongs to a company
//	private boolean foundInCompanysCoupons(long couponId, long companyId) {
//		List<Coupon> companyCoupons = getCompanyCoupons(companyId);
//		for (Coupon c : companyCoupons) {
//			if (couponId == c.getId())
//				return true;
//		}
//		return false;
//	}

}
