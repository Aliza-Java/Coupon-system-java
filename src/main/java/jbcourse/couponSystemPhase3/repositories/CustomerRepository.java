package jbcourse.couponSystemPhase3.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jbcourse.couponSystemPhase3.entities.Company;
import jbcourse.couponSystemPhase3.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	//For login
	@Query("select c from Customer c where c.name=?1 and c.password=?2")
	Optional<Customer> getCustomerByNameAndPassword(String username, String password);
	
	//Defined explicitly to allow for sorting by id
	@Query
	List<Customer> findByOrderByIdAsc();
	
	@Query
	Optional<Customer> findByName(String name);
}
