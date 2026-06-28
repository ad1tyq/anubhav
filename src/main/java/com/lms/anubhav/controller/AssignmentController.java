package com.lms.anubhav.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.lms.anubhav.dto.AssignmentCreateRequest;
import com.lms.anubhav.model.Assignment;
import com.lms.anubhav.service.AssignmentService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AssignmentController {

  private final AssignmentService assignmentService;

  @PostMapping("/courses/{courseId}/assignments")
  public ResponseEntity<Assignment> createAssignment(
      @PathVariable Long courseId,
      @RequestBody AssignmentCreateRequest request) {

    Assignment newAssignment = assignmentService.createAssignment(courseId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(newAssignment);
  }
}
