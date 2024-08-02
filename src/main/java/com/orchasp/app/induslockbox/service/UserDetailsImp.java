package com.orchasp.app.induslockbox.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.orchasp.app.induslockbox.entity.Admin;
import com.orchasp.app.induslockbox.repository.AdminRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDetailsImp implements UserDetails,UserDetailsService{
	
	@Autowired
	private  AdminRepository repository;
	   private Admin admin;

	    public UserDetailsImp(Admin admin) {
	        this.admin = admin;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        // Convert the role to a granted authority
			return Collections.singletonList(new SimpleGrantedAuthority(admin.getRole().name()));
	    }

	    @Override
	    public String getPassword() {
	        return admin.getPassword();
	    }

	    @Override
	    public String getUsername() {
	        return admin.getEmail();
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

	    public Admin getAdmin() {
	        return admin;
	    }

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//	    log.info("Looking up user by email: " + username);
	    Admin admin = repository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
	    return new UserDetailsImp(admin);
	}

	

}
