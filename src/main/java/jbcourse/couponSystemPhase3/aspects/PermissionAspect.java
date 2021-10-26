package jbcourse.couponSystemPhase3.aspects;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jbcourse.couponSystemPhase3.exceptions.PermissionException;
import jbcourse.couponSystemPhase3.util_classes.LoginType;
import jbcourse.couponSystemPhase3.util_classes.User;

/*
 * An aspect disallowing company/customer to touch methods that are not of their user.
 */

//Currently not relevant since we are not using sessions.
@Aspect
@Configuration
public class PermissionAspect {
	
	@Autowired 
	HttpSession session;

	
	@Before("execution( * jbcourse.couponSystemPhase3.rest.CompanyWebService.*(..))")
	public void ValidatePermissionToAccessCompanyMethod() throws PermissionException {
				
		if(!LoginType.COMPANY.equals(((User) session.getAttribute("user")).getType())) {
			throw new PermissionException("Only companies can access this function.");
		}
	}
	
	@Before("execution( * jbcourse.couponSystemPhase3.rest.CustomerWebService.*(..))")
	public void ValidatePermissionToAccessCustomerMethod() throws PermissionException {
				
		if(!LoginType.CUSTOMER.equals(((User) session.getAttribute("user")).getType())) {
			throw new PermissionException("Only customers can access this function.");
		}
	}
}