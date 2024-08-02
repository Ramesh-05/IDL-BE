package com.orchasp.app.induslockbox.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Admin;
import com.orchasp.app.induslockbox.repository.AdminRepository;

@Service
public class OtpService {
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder encoder;

	private Map<String, String> otpStorage = new HashMap<>();

	public String generateOtp(String identifier) {
		Random random = new Random();
		String otp = String.format("%06d", random.nextInt(999999));
		otpStorage.put(identifier, otp);
		return otp;
	}

	public boolean validateOtp(String identifier, String otp) {
		return otp.equals(otpStorage.get(identifier));
	}

	public void sendOtpEmail(String email, String otp) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("digambarbulbule123@gmail.com");
			message.setTo(email);
			message.setSubject("Your OTP Code");
			message.setText("Your OTP code is: " + otp);
			mailSender.send(message);
			System.out.println("Mail sent successfully to " + email);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error sending email: " + e.getMessage());
		}
	}

	public ResponseEntity<?> sendOtp(String email) {
		Optional<Admin> userOpt = adminRepository.findByEmail(email);
		if (!userOpt.isPresent()) {
//	            userOpt = adminRepository.findByMobile(email);
//	            if (!userOpt.isPresent()) {
			return ResponseEntity.badRequest().body("User not found");
//	            }
		}
		Admin user = userOpt.get();
		String otp = generateOtp(email);
		if (email.contains("@")) {
			sendOtpEmail(user.getEmail(), otp);
		} else {
			sendOtpEmail(user.getMobile(), otp);
		}
		return ResponseEntity.ok("OTP sent");
	}

	public ResponseEntity<?> verifyOtp(String email, String otp) {
		boolean isValid = validateOtp(email, otp);
		if (isValid) {
			return ResponseEntity.ok("OTP verified");
		} else {
			return ResponseEntity.badRequest().body("Invalid OTP");
		}
	}

	public ResponseEntity<?> changePassword(String email,String otp,String newPassword) {
		boolean isValid = validateOtp(email, otp);
		if (!isValid) {
			return ResponseEntity.badRequest().body("Invalid OTP");
		}

		Optional<Admin> adminOpt = adminRepository.findByEmail(email);
		if (!adminOpt.isPresent()) {
			adminOpt = adminRepository.findByEmail(email);
	            if (!adminOpt.isPresent()) {
			return ResponseEntity.badRequest().body("User not found");
	            }
		}
		Admin admin = adminOpt.get();
		admin.setPassword(encoder.encode(newPassword));
		adminRepository.save(admin);
		return ResponseEntity.ok("Password changed successfully");
	}

}
