package com.example.service;

import com.example.model.BranchManager;
import com.example.repository.BranchManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchManagerService {

	@Autowired
	private BranchManagerRepository branchManagerRepository;

	public List<BranchManager> getAllBranchManagers() {
		return branchManagerRepository.findAll();
	}

	public Optional<BranchManager> getBranchManagerById(Long id) {
		return branchManagerRepository.findById(id);
	}

	public BranchManager createBranchManager(BranchManager branchManager) {
		return branchManagerRepository.save(branchManager);
	}

	public BranchManager updateBranchManager(Long id, BranchManager branchManagerDetails) {
		BranchManager branchManager = branchManagerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Branch Manager not found"));
		branchManager.setName(branchManagerDetails.getName());
		branchManager.setDob(branchManagerDetails.getDob());
		branchManager.setGender(branchManagerDetails.getGender());
		branchManager.setPhone(branchManagerDetails.getPhone());
		branchManager.setEmail(branchManagerDetails.getEmail());
		branchManager.setPassword(branchManagerDetails.getPassword());
		branchManager.setBranch(branchManagerDetails.getBranch());
		branchManager.setPresentAddress(branchManagerDetails.getPresentAddress());
		branchManager.setPermanentAddress(branchManagerDetails.getPermanentAddress());
		return branchManagerRepository.save(branchManager);
	}

	public void deleteBranchManager(Long id) {
		BranchManager branchManager = branchManagerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Branch Manager not found"));
		branchManagerRepository.delete(branchManager);
	}
}
