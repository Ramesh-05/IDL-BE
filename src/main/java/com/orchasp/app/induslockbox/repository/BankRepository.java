package com.orchasp.app.induslockbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orchasp.app.induslockbox.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
	@Query("FROM Bank b JOIN FETCH b.company c WHERE c.id = :companyid")
	List<Bank> findByCompany_id(@Param("companyid") Long companyid);
	
	 List<Bank> findByActiveTrue();
	    List<Bank> findByActiveFalse();
}
