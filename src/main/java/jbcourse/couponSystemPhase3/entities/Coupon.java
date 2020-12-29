package jbcourse.couponSystemPhase3.entities;

import java.time.LocalDate;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jbcourse.couponSystemPhase3.exceptions.CouponDateException;
import jbcourse.couponSystemPhase3.util_classes.CouponCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor

@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "Coupon must have a title.")
	@Size(min = 2, message = "Minimum length for coupon title is 2 characters.")
	@NotBlank(message = "coupon title cannot be blank.")
	private String title;

	@NotNull(message = "Coupon must have a start date.")
	@DateTimeFormat(pattern = "yyyy-MMM-dd")
	private LocalDate startDate;

	@Future(message = "This coupon seems to have already expired.")
	@NotNull(message = "Coupon must have an end date.")
	@DateTimeFormat(pattern = "yyyy-MMM-dd")
	private LocalDate endDate;

	@Min(0)
	@NumberFormat
	private int amount;

	@Enumerated(EnumType.STRING)
	private CouponCategory category;

	private String message;

	@Min(value = 0, message = "Price cannot be negative.")
	private double price;

	private String image;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	@Valid
	Company company;

	public Coupon(long id, String title, LocalDate startDate, LocalDate endDate, int amount, CouponCategory category,
			String message, double price, String image) throws CouponDateException {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.category = category;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	// a helper method to sort coupons when retrieved by a simple getter (not
	// directly from database)
	public static Comparator<Coupon> byId = Comparator.comparing(coupon -> coupon.id);

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", category=" + category + ", message=" + message + ", price=" + price
				+ ", image=" + image + "]";
	}

}
