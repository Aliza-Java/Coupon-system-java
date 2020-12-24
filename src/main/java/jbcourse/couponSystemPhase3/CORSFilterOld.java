//
//package jbcourse.couponSystemPhase3;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
////@Configuration
//public class CORSFilterOld implements Filter {
//
//	@Value("${cors.origin}")
//	private String preDefinedCorsOrigin;
//
//	public void destroy() {
//
//	}
//
//	
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		// Lets make sure that we are working with HTTP (that is, against
//		// HttpServletRequest and HttpServletResponse objects)
//		// if (req instanceof HttpServletRequest && res instanceof HttpServletResponse)
//		// {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//		// Access-Control-Allow-Origin
//		httpResponse.setHeader("Access-Control-Allow-Origin", preDefinedCorsOrigin);
//		httpResponse.setHeader("Vary", "Origin");
//
//		// Access-Control-Max-Age
//		httpResponse.setHeader("Access-Control-Max-Age", "3600");
//
//		// Access-Control-Allow-Credentials
//		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//
//		// Access-Control-Allow-Methods
//		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, PATCH, HEAD, TRACE, OPTIONS, DELETE");
//		httpResponse.setHeader("Access-Control-Allow-Headers", "Content-type, Accept");
//		
//		if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
//
//			httpResponse.setStatus(HttpServletResponse.SC_OK);
//		}
//					
//		chain.doFilter(httpRequest, httpResponse);
//
//	}
//
//	public void init(FilterConfig filterConfig) {
//	}
//}
