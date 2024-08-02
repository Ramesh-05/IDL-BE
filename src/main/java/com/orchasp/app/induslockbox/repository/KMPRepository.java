package com.orchasp.app.induslockbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.orchasp.app.induslockbox.entity.KMP;

public interface KMPRepository extends JpaRepository<KMP, Long>{

	@Query("FROM KMP b JOIN FETCH b.company c WHERE c.id = :companyid")
	List<KMP> findByCompany_id(@Param("companyid") Long companyid);
	
	 List<KMP> findByActiveTrue();
	    List<KMP> findByActiveFalse();
}
