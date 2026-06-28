package com.lms.anubhav.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.lms.anubhav.dto.GradeRequest;
import com.lms.anubhav.dto.SubmissionRequest;
import com.lms.anubhav.dto.SubmissionResponse;
import com.lms.anubhav.model.AssignmentSubmission;
import com.lms.anubhav.service.AssignmentSubmissionService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AssignmentSubmissionController {

  private final AssignmentSubmissionService submissionService;

  @PostMapping("/assignments/{assignmentId}/submit")
  public ResponseEntity<AssignmentSubmission> submitWork(
      @PathVariable Long assignmentId,
      @RequestBody SubmissionRequest request) {

    AssignmentSubmission submission = submissionService.submitAssignment(assignmentId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(submission);
  }

  @GetMapping("/assignments/{assignmentId}/submissions")
  public ResponseEntity<List<SubmissionResponse>> getSubmissions(@PathVariable Long assignmentId) {
    return ResponseEntity.ok(submissionService.getSubmissionsByAssignment(assignmentId));
  }

  @PutMapping("/submissions/{submissionId}/grade")
  public ResponseEntity<AssignmentSubmission> gradeSubmission(
      @PathVariable Long submissionId,
      @RequestBody GradeRequest request) {
    AssignmentSubmission gradedSubmission = submissionService.gradeSubmission(submissionId, request);
    return ResponseEntity.ok(gradedSubmission);
  }

  @GetMapping("/assignments/{assignmentId}/submissions/students/{studentId}")
  public ResponseEntity<SubmissionResponse> getMyGrade(
      @PathVariable Long assignmentId,
      @PathVariable Long studentId) {

    SubmissionResponse mySubmission = submissionService.getStudentSubmission(assignmentId, studentId);
    return ResponseEntity.ok(mySubmission);
  }

}
