package com.orchasp.app.induslockbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orchasp.app.induslockbox.entity.EPF;

@Repository
public interface EPFRepository extends JpaRepository<EPF, Long> {

	@Query("FROM EPF b JOIN FETCH b.company c WHERE c.id = :companyid")
	List<EPF> findByCompany_id(@Param("companyid") Long companyid);

	List<EPF> findByActiveTrue();

	List<EPF> findByActiveFalse();
}
