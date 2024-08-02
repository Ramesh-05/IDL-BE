package com.orchasp.app.induslockbox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orchasp.app.induslockbox.request.SignInRequest;
import com.orchasp.app.induslockbox.request.SignUpRequest;
import com.orchasp.app.induslockbox.service.AuthenticationService;
import com.orchasp.app.induslockbox.service.OtpService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AdminController {
	@Autowired
	private AuthenticationService service;
	@Autowired
	private OtpService service2;

//	http://localhost:8082/api/auth/signup
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest request) {
		return ResponseEntity.ok(service.signup(request));
	}

//	http://localhost:8082/api/auth/signin
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@Valid @RequestBody SignInRequest request) {
		return ResponseEntity.ok(service.signin(request));
	}

	@PostMapping("/requestOtp")
	public ResponseEntity<?> sendOtpToMail(@RequestParam String email) {
		return ResponseEntity.ok(service2.sendOtp(email));
	}

	@PostMapping("/verifyOtp")
	public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
		return ResponseEntity.ok(service2.verifyOtp(email, otp));
	}

	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestParam String email, @RequestParam String otp,@RequestParam String newPassword) {
		return ResponseEntity.ok(service2.changePassword(email, otp, newPassword));
	}

	@GetMapping("/home")
	public String local() {
		return "Local home";
	}

}
