package com.example.controller;

import com.example.model.BranchManager;
import com.example.service.BranchManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BranchManagerController {

	@Autowired
	private BranchManagerService branchManagerService;

	@GetMapping("/listAllBranchManagers")
	public List<BranchManager> getAllBranchManagers() {
		return branchManagerService.getAllBranchManagers();
	}

	@GetMapping("/branchManager/{id}")
	public ResponseEntity<BranchManager> getBranchManagerById(@PathVariable Long id) {
		BranchManager branchManager = branchManagerService.getBranchManagerById(id)
				.orElseThrow(() -> new RuntimeException("Branch Manager not found"));
		return ResponseEntity.ok(branchManager);
	}

	@PostMapping("/createBranchManager")
	public BranchManager createBranchManager(@RequestBody BranchManager branchManager) {
		return branchManagerService.createBranchManager(branchManager);
	}

	@PutMapping("/updateBranchManager/{id}")
	public ResponseEntity<BranchManager> updateBranchManager(@PathVariable Long id,
			@RequestBody BranchManager branchManagerDetails) {
		BranchManager updatedBranchManager = branchManagerService.updateBranchManager(id, branchManagerDetails);
		return ResponseEntity.ok(updatedBranchManager);
	}

	@DeleteMapping("/deleteBranchManager/{id}")
	public ResponseEntity<Void> deleteBranchManager(@PathVariable Long id) {
		branchManagerService.deleteBranchManager(id);
		return ResponseEntity.noContent().build();
	}
}
