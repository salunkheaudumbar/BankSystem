package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	private String otp;

	public void sendOtpEmail(String toEmail) {
		Random random = new Random();
		otp = String.format("%06d", random.nextInt(1000000));
		String subject = "Your OTP Code";
		String text = "Your OTP code is: " + otp;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);
	}

	public boolean verifyOtp(String inputOtp) {
		return otp != null && otp.equals(inputOtp);
	}
}
