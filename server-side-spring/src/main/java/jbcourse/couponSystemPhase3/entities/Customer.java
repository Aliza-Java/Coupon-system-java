package jbcourse.couponSystemPhase3.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "Username must be entered.")
	@Size(min = 2, message = "Minimum length for username is 2 characters.")
	@NotBlank(message = "Username cannot be blank.")
	private String name;

	@NotNull
	@NotBlank
	@Size(min = 4, message = "Password must be at least 4 characters long.")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@Valid
	List<Coupon> coupons;

	public Customer(String name, String password, List<Coupon> coupons) {
		super();
		this.name = name;
		this.password = password;
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", password=" + password + ", coupons=" + /* coupons + */ "]";
	}

}
