package jbcourse.couponSystemPhase3;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

//@Component 
// not sure we need this, going stateless without cookies.  
public class SameSiteFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Collection<String> headers = res.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;
        for (String header : headers) { // there can be multiple Set-Cookie attributes
            if (firstHeader) {
                res.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s",  header, "SameSite=None;"));

                firstHeader = false;
                continue;
            }

            res.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s",  header, "SameSite=None"));
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}