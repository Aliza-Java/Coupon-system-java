package jbcourse.couponSystemPhase3.authentication;

import org.springframework.security.core.Authentication;

/*
 * To fully leverage the Spring dependency injection and be able to retrieve the authentication everywhere, 
 * not just in @Controller beans, we need to hide the static access behind a simple facade 
 * - from https://www.baeldung.com/get-user-in-spring-security
*
 * */
public interface IAuthenticationFacade {
	Authentication getAuthentication();
}
