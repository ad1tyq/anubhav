package com.lms.anubhav.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lms.anubhav.dto.GradeRequest;
import com.lms.anubhav.dto.SubmissionRequest;
import com.lms.anubhav.dto.SubmissionResponse;
import com.lms.anubhav.model.Assignment;
import com.lms.anubhav.model.AssignmentSubmission;
import com.lms.anubhav.model.User;
import com.lms.anubhav.repository.AssignmentRepository;
import com.lms.anubhav.repository.AssignmentSubmissionRepository;
import com.lms.anubhav.repository.EnrollmentRepository;
import com.lms.anubhav.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionService {

  private final AssignmentSubmissionRepository submissionRepository;
  private final AssignmentRepository assignmentRepository;
  private final UserRepository userRepository;
  private final EnrollmentRepository enrollmentRepository;

  @Transactional
  public AssignmentSubmission submitAssignment(Long assignmentId, SubmissionRequest request) {
    User student = userRepository.findById(request.getStudentId())
        .orElseThrow(() -> new RuntimeException("Student not found"));

    Assignment assignment = assignmentRepository.findById(assignmentId)
        .orElseThrow(() -> new RuntimeException("Assignment not found"));

    if (!enrollmentRepository.existsByStudentAndCourse(student, assignment.getCourse())) {
      throw new RuntimeException("Unauthorized: Student is not enrolled in this course");
    }

    if (submissionRepository.existsByAssignmentAndStudent(assignment, student)) {
      throw new RuntimeException("Assignment already submitted");
    }

    if (LocalDateTime.now().isAfter(assignment.getDueDate())) {
      throw new RuntimeException("Submission deadline has passed");
    }

    AssignmentSubmission submission = new AssignmentSubmission();
    submission.setAssignment(assignment);
    submission.setStudent(student);
    submission.setSubmissionUrl(request.getSubmissionUrl());
    submission.setSubmittedAt(LocalDateTime.now());
    submission.setStatus("PENDING_GRADING");

    return submissionRepository.save(submission);

  }

  @Transactional
  public List<SubmissionResponse> getSubmissionsByAssignment(Long assignmentId) {
    List<AssignmentSubmission> submissions = submissionRepository.findByAssignmentId(assignmentId);

    return submissions.stream().map(sub -> {
      SubmissionResponse dto = new SubmissionResponse();
      dto.setSubmissionId(sub.getId());
      dto.setStudentId(sub.getStudent().getId());
      dto.setStudentName(sub.getStudent().getFirstName() + " " + sub.getStudent().getLastName());
      dto.setSubmissionUrl(sub.getSubmissionUrl());
      dto.setSubmittedAt(sub.getSubmittedAt());
      dto.setStatus(sub.getStatus());
      return dto;
    }).collect(Collectors.toList());
  }

  @Transactional
  public AssignmentSubmission gradeSubmission(Long submissionId, GradeRequest request) {
    AssignmentSubmission submission = submissionRepository.findById(submissionId)
        .orElseThrow(() -> new RuntimeException("Submission not found"));

    Long courseTeacherId = submission.getAssignment().getCourse().getTeacher().getId();
    if (!courseTeacherId.equals(request.getTeacherId())) {
      throw new RuntimeException("Unauthorized: Only the assigned teacher can grade this submission");
    }

    BigDecimal maxMarks = new BigDecimal(submission.getAssignment().getTotalMarks());
    if (request.getMarksObtained().compareTo(maxMarks) > 0) {
      throw new RuntimeException("Marks obtained cannot exceed total marks for the assignment");
    }

    submission.setMarksObtained(request.getMarksObtained());
    submission.setFeedback(request.getFeedback());
    submission.setStatus("GRADED");

    return submissionRepository.save(submission);
  }

  @Transactional
  public SubmissionResponse getStudentSubmission(Long assignmentId, Long studentId) {

    AssignmentSubmission submission = submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId)
        .orElseThrow(() -> new RuntimeException("Submission not found for this student"));

    SubmissionResponse response = new SubmissionResponse();
    response.setSubmissionId(submission.getId());
    response.setStudentId(submission.getStudent().getId());
    response.setStudentName(submission.getStudent().getFirstName() + " " + submission.getStudent().getLastName());
    response.setSubmissionUrl(submission.getSubmissionUrl());
    response.setSubmittedAt(submission.getSubmittedAt());
    response.setStatus(submission.getStatus());

    response.setMarksObtained(submission.getMarksObtained());
    response.setFeedback(submission.getFeedback());

    return response;
  }

}
