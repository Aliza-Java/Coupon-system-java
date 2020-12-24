package jbcourse.couponSystemPhase3.authentication;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import jbcourse.couponSystemPhase3.exceptions.LoginException;
import jbcourse.couponSystemPhase3.util_classes.UserDetailsImpl;

//All utility functions having to do with the token itself.

@Component
public class JwtTokenUtil implements Serializable {

	@Autowired
	UserDetailsServiceImpl uDService;
	
	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration.ms}")
	private int jwtExpiration;

	// retrieve username from jwt token
	public long getIdFromToken(String token) {
		try {
			UserDetailsImpl user = (UserDetailsImpl) uDService.loadUserByUsername(getUsernameFromJwtToken(token));			

		return user.getId();

			// } catch (JwtException | ClassCastException e) {
		} catch (Exception e) {
			throw e;
		}
	}

	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user - original from javainuse.com
//	public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		return doGenerateToken(claims, userDetails.getUsername());
//	}

	// preferred data stored, hence preferred directions from toptal.com
	public String generateToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
//	private String doGenerateToken(Map<String, Object> claims, String subject) {
//
//		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//	}

	/*
	 * //validate token public Boolean validateToken(String token, UserDetails
	 * userDetails) { final String username = getUsernameFromToken(token); return
	 * (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); }
	 */

	// validate token
	public boolean validateJwtToken(String authToken) throws LoginException {
		if (isTokenExpired(authToken)) {
			throw new LoginException("Token is expired.");
		}

		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

			return true;
		} catch (SignatureException e) {
			return false;
		}
	}
}