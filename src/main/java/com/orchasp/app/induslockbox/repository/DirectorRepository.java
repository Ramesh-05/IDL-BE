package com.orchasp.app.induslockbox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orchasp.app.induslockbox.entity.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

	@Query("FROM Director b JOIN FETCH b.company c WHERE c.id = :companyid")
	List<Director> findByCompany_id(@Param("companyid") Long companyid);

	List<Director> findByActiveTrue();

	List<Director> findByActiveFalse();
}
