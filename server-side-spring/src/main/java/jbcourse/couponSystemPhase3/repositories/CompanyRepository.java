package jbcourse.couponSystemPhase3.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jbcourse.couponSystemPhase3.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	//For login
	@Query("select c from Company c where c.name=?1 and c.password=?2")
	Optional<Company> getCompanyByNameAndPassword(String username, String password);
	
	//Defined this method explicitly in order to sort by Id in ascending order
	@Query
	List<Company> findByOrderByIdAsc();
}
