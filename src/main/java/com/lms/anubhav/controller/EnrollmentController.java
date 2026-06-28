package com.lms.anubhav.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lms.anubhav.dto.EnrollmentCreateRequest;
import com.lms.anubhav.dto.StudentCourseResponse;
import com.lms.anubhav.model.Enrollment;
import com.lms.anubhav.service.EnrollmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EnrollmentController {

  private final EnrollmentService enrollmentService;

  @PostMapping("/courses/{courseId}/enroll")
  public ResponseEntity<Enrollment> enrollStudent(
      @PathVariable Long courseId,
      @RequestBody EnrollmentCreateRequest request) {

    Enrollment newEnrollment = enrollmentService.createEnrollment(courseId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(newEnrollment);
  }

  @GetMapping("/students/{studentId}/courses")
  public ResponseEntity<List<StudentCourseResponse>> getStudentDashboard(@PathVariable Long studentId) {
    List<StudentCourseResponse> dashboard = enrollmentService.getStudentCourses(studentId);
    return ResponseEntity.ok(dashboard);
  }

}
