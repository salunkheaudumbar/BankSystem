package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User register(User user) {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User login(String email, String password) {
		return userRepository.findByEmail(email).filter(user -> passwordEncoder.matches(password, user.getPassword()))
				.orElse(null);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void updatePassword(String email, String newPassword) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}
	
	public Optional<String> getPhoneNumberByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(User::getPhone);
    }

}
