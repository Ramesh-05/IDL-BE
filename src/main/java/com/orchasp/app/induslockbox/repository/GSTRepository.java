package com.orchasp.app.induslockbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orchasp.app.induslockbox.entity.GST;

@Repository
public interface GSTRepository extends JpaRepository<GST, Long> {

	@Query("FROM GST g JOIN FETCH g.company c WHERE c.id = :companyid")
	List<GST> findByCompany_id(@Param("companyid") Long companyid);

	List<GST> findByActiveTrue();

	List<GST> findByActiveFalse();
}
