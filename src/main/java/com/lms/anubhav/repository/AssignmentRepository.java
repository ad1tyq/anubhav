package com.lms.anubhav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.anubhav.model.Assignment;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
  // JpaRepository provides findById() by default
}
