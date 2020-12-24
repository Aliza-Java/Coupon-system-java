package jbcourse.couponSystemPhase3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import jbcourse.couponSystemPhase3.exceptions.ObjectNotFoundException;

//@EnableScheduling
@SpringBootApplication
@ServletComponentScan
@EnableAspectJAutoProxy



public class CouponSystemPhase3Application {
	
	public static void main(String[] args) throws ObjectNotFoundException {
		SpringApplication.run(CouponSystemPhase3Application.class, args);

	}
	


}
