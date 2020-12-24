package jbcourse.couponSystemPhase3.authentication;

import java.io.Serializable;

//Used to return a response to user, with a token.  

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final long id;

	public JwtResponse(String jwttoken, long id) {
		this.jwttoken = jwttoken;
		this.id = id;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public long getId() {
		return this.id;
	}
}