
package com.orchasp.app.induslockbox.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Admin;
import com.orchasp.app.induslockbox.repository.AdminRepository;

@Service
//@RequiredArgsConstructor
public class AdminService {
	@Autowired
	private AdminRepository repository;
	
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			
			@Override
			 public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		        Admin admin = repository.findByEmail(email)
		                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		        return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(), new ArrayList<>());
		    }
		};
	}

}
