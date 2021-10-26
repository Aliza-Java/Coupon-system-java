//This seems superfluous, authenticate is taken care of in JwtAuthenticationController
//package jbcourse.couponSystemPhase3.authentication;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//public class CustomAuthenticationManager implements AuthenticationManager{
//
//
//    	@Override
//        public Authentication authenticate(Authentication auth) throws AuthenticationException {
//            String username = auth.getName();
//            String password = auth.getCredentials().toString();
//            
//            List<GrantedAuthority> grantedAuths = new ArrayList<>();
//            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
//            return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
//        }
//
//    }
//
//
//
