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
@WebFilter("/sec/*")
public class LoginFilter implements Filter {

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		//Preflight request doesn't send a session, so don't confuse it with an unsecure entry. 
		if (session == null&&(!"OPTIONS".equalsIgnoreCase(httpRequest.getMethod())))  {
			httpResponse.sendError(401, "You are not logged in.");

		} else {
			chain.doFilter(httpRequest, httpResponse);
		}

	}

}
