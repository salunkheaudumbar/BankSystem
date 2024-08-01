package com.example.repository;

import com.example.model.BranchManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchManagerRepository extends JpaRepository<BranchManager, Long> {
}
