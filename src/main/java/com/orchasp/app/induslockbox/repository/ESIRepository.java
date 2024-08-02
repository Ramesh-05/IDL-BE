package com.orchasp.app.induslockbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orchasp.app.induslockbox.entity.ESI;

@Repository
public interface ESIRepository extends JpaRepository<ESI, Long> {

	@Query("FROM ESI b JOIN FETCH b.company c WHERE c.id = :companyid")
	List<ESI> findByCompany_id(@Param("companyid") Long companyid);
	
	 List<ESI> findByActiveTrue();
	    List<ESI> findByActiveFalse();

}