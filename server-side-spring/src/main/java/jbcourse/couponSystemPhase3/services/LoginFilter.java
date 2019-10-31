package jbcourse.couponSystemPhase3.services;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

//Overcoming CORS while allowing cookies
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true",
methods= {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT, RequestMethod.TRACE})
@WebFilter("/sec/*")
public class LoginFilter implements Filter {

	@Override
	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			httpResponse.sendError(401, "You are not logged in.");

		} else {
			chain.doFilter(httpRequest, httpResponse);
		}

	}

}
