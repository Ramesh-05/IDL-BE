package com.orchasp.app.induslockbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.orchasp.app.induslockbox.entity.TDS;

@Repository
public interface TDSRepository extends JpaRepository<TDS,Long> {
	@Query("FROM TDS t JOIN FETCH t.company c WHERE c.id = :companyid")
	List<TDS> findByCompany_id(@Param("companyid") Long companyid);
	
	 List<TDS> findByActiveTrue();
	    List<TDS> findByActiveFalse();

}
