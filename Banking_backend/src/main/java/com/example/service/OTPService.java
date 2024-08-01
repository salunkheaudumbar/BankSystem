package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPService {

	private String otp;
	private String phoneNumber;

	@Autowired
	private TwilioService twilioService;

	public void generateAndSendOTP(String phoneNumber) {
		this.setPhoneNumber(phoneNumber);
		Random random = new Random();
		otp = String.format("%06d", random.nextInt(1000000));
		String message = "Your OTP is: " + otp;
		twilioService.sendSMS(phoneNumber, message);
	}

	public boolean verifyOTP(String inputOtp) {
		return otp.equals(inputOtp);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
