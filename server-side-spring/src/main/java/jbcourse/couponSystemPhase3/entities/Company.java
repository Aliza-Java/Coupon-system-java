package jbcourse.couponSystemPhase3.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "Username must be entered.")
	@Size(min = 2, message = "Minimum length for username is 2 characters.")
	@NotBlank(message = "Username cannot be blank.")
	private String name;

	@NotNull
	@NotBlank
	@Size(min = 4, message = "Password must be at least 4 characters.")
	private String password;

	@NotNull(message = "Company must have an email address.")
	@Email(message = "Email address is not valid.")
	private String email;

	@OneToMany(mappedBy = "company", cascade = { CascadeType.REMOVE, CascadeType.MERGE })
	@Valid
	List<Coupon> coupons;

	public Company(String name, String password, String email) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", coupons="
				+ coupons + "]";
	}

}
