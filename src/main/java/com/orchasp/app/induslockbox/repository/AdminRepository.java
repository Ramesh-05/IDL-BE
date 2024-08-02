package com.orchasp.app.induslockbox.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orchasp.app.induslockbox.entity.Admin;
import com.orchasp.app.induslockbox.entity.Role;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByUserName(String userName);

	Optional<Admin> findByEmail(String email);

	Boolean existsByUserName(String userName);

	Boolean existsByMobile(String mobile);

	Boolean existsByEmail(String email);

	Admin findByRole(Role role);

}
