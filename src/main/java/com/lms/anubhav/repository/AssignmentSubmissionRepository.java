package com.lms.anubhav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.anubhav.model.AssignmentSubmission;
import com.lms.anubhav.model.Assignment;
import com.lms.anubhav.model.User;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
  boolean existsByAssignmentAndStudent(Assignment assignment, User student);

  List<AssignmentSubmission> findByAssignmentId(Long assignmentId);

  java.util.Optional<AssignmentSubmission> findByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
}
