package com.example.controller;

import com.example.service.EmailService;
import com.example.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class OTPController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private TwilioService twilioService;

	@PostMapping("/send-otp")
	public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
		String contactType = request.get("contactType");
		String contactValue = request.get("contactValue");

		if ("email".equalsIgnoreCase(contactType)) {
			emailService.sendOtpEmail(contactValue);
			return ResponseEntity.ok().body("OTP sent to email: " + contactValue);
		} else if ("phone".equalsIgnoreCase(contactType)) {
			String otp = twilioService.generateOtp();
			String message = "Your OTP is: " + otp;
			twilioService.sendSMS(contactValue, message);
			return ResponseEntity.ok().body("OTP sent to phone number: " + contactValue);
		} else {
			return ResponseEntity.badRequest().body("Invalid contact type");
		}
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
		String otp = request.get("otp");
		boolean isValidEmailOtp = emailService.verifyOtp(otp);
		boolean isValidPhoneOtp = twilioService.verifyOtp(otp);

		if (isValidEmailOtp || isValidPhoneOtp) {
			return ResponseEntity.ok().body("OTP verified successfully");
		} else {
			return ResponseEntity.badRequest().body("Invalid OTP");
		}
	}
}
