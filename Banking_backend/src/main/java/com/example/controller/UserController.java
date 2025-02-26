package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register/employee")
	public ResponseEntity<?> registerEmployee(@RequestBody User user) {
		user.setRole("EMPLOYEE");
		return ResponseEntity.ok(userService.register(user));
	}

	@PostMapping("/register/manager")
	public ResponseEntity<?> registerManager(@RequestBody User user) {
		user.setRole("ADMIN");
		return ResponseEntity.ok(userService.register(user));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		User loggedInUser = userService.login(user.getEmail(), user.getPassword());
		if (loggedInUser != null) {
			return ResponseEntity.ok(loggedInUser);
		} else {
			return ResponseEntity.badRequest().body("Invalid email or password");
		}
	}

	@PostMapping("/manager-login")
	public ResponseEntity<?> managerLogin(@RequestBody User user) {
		User loggedInUser = userService.login(user.getEmail(), user.getPassword());
		if (loggedInUser != null && "ADMIN".equals(loggedInUser.getRole())) {
			return ResponseEntity.ok(loggedInUser);
		} else {
			return ResponseEntity.badRequest().body("Invalid email or password");
		}
	}

	@GetMapping("/employees")
	public List<User> getAllEmployees() {
		return userService.getAllUsers();
	}

	@PutMapping("/update-password")
	public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String newPassword = request.get("newPassword");
		userService.updatePassword(email, newPassword);
		return ResponseEntity.ok("Password updated successfully");
	}

	@GetMapping("/getPhoneNumberByEmail")
	public ResponseEntity<String> getPhoneNumberByEmail(@RequestParam String email) {
		Optional<String> phoneNumber = userService.getPhoneNumberByEmail(email);
		if (phoneNumber.isPresent()) {
			return ResponseEntity.ok(phoneNumber.get());
		} else {
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Phone number not found for the provided email.");
		}
	}

}
