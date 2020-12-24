package jbcourse.couponSystemPhase3.authentication;

import jbcourse.couponSystemPhase3.util_classes.LoginType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Login {

	private String username;
	private String password;
	private LoginType type;

}
