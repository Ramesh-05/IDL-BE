package com.orchasp.app.induslockbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Admin;
import com.orchasp.app.induslockbox.entity.Role;
import com.orchasp.app.induslockbox.repository.AdminRepository;
import com.orchasp.app.induslockbox.request.SignInRequest;
import com.orchasp.app.induslockbox.request.SignUpRequest;
import com.orchasp.app.induslockbox.response.LoginResponse;
import com.orchasp.app.induslockbox.response.MessageResponse;

@Service
//@RequiredArgsConstructor
public class AuthenticationService {
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtServiceImpl jwtServiceImpl;

	@Autowired
	private UserDetailsImp userDetailsImp;

//	@Autowired
//	private AdminService adminService;

	public ResponseEntity<?> signup(SignUpRequest request) {
		if (adminRepository.existsByUserName(request.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (adminRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
		}
		if (adminRepository.existsByMobile(request.getMobile())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Mobile number is already taken!"));
		}

		Admin admin = new Admin();
		admin.setUserName(request.getUserName());
		admin.setEmail(request.getEmail());
		admin.setPassword(encoder.encode(request.getPassword()));
		admin.setMobile(request.getMobile());
		if (request.getRole() != null && request.getRole().name().equalsIgnoreCase("ROLE_ADMIN")) {
			admin.setRole(Role.ROLE_ADMIN);
		} else {
			admin.setRole(Role.ROLE_USER);
		}
		adminRepository.save(admin);
		return ResponseEntity.ok(new MessageResponse("Registration Successful"));
	}

	public ResponseEntity<?> signin(SignInRequest request) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			Admin admin = adminRepository.findByEmail(request.getEmail())
					.orElseThrow(() -> new IllegalArgumentException("Invalid Email..!"));

			UserDetailsImp userDetails = (UserDetailsImp) userDetailsImp.loadUserByUsername(request.getEmail());

			String token = jwtServiceImpl.generateToken(userDetails);

			LoginResponse response = new LoginResponse();
			response.setId(admin.getId());
			response.setUserName(admin.getUserName());
			response.setMessage("Login Successful");
			response.setPassword(admin.getPassword());
			response.setEmail(admin.getEmail());
			response.setMobile(admin.getMobile());
			response.setRole(admin.getRole());
			response.setToken(token);
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(401).body(new MessageResponse("Error: Invalid email or password!"));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(new MessageResponse("Error: Something went wrong!"));
		}
	}
}
