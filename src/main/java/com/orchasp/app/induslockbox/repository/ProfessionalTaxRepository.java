package com.orchasp.app.induslockbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.orchasp.app.induslockbox.entity.ProfessionalTax;

public interface ProfessionalTaxRepository extends JpaRepository<ProfessionalTax, Long> {
	@Query("FROM ProfessionalTax p JOIN FETCH p.company c WHERE c.id = :companyid")
	List<ProfessionalTax> findByCompany_id(@Param("companyid") Long companyid);
}
