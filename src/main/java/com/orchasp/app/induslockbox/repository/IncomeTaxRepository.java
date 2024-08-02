package com.orchasp.app.induslockbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orchasp.app.induslockbox.entity.IncomeTax;

@Repository
public interface IncomeTaxRepository extends JpaRepository<IncomeTax, Long> {
	
	@Query("FROM IncomeTax i JOIN FETCH i.company c WHERE c.id = :companyid")
	List<IncomeTax> findByCompany_id(@Param("companyid") Long companyid);

	List<IncomeTax> findByActiveTrue();

	List<IncomeTax> findByActiveFalse();
}
