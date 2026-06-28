package com.lms.anubhav.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lms.anubhav.dto.EnrollmentCreateRequest;
import com.lms.anubhav.dto.StudentCourseResponse;
import com.lms.anubhav.model.Course;
import com.lms.anubhav.model.Enrollment;
import com.lms.anubhav.model.User;
import com.lms.anubhav.repository.CourseRepository;
import com.lms.anubhav.repository.EnrollmentRepository;
import com.lms.anubhav.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

  private final EnrollmentRepository enrollmentRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;

  @Transactional
  public Enrollment createEnrollment(Long courseId, EnrollmentCreateRequest request) {

    User student = userRepository.findById(request.getStudentId())
        .orElseThrow(() -> new RuntimeException("Student not found"));

    Course course = courseRepository.findById(courseId)
        .orElseThrow(() -> new RuntimeException("Course not found"));

    if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
      throw new RuntimeException("Student is already enrolled in this course");
    }

    Enrollment enrollment = new Enrollment();
    enrollment.setStudent(student);
    enrollment.setCourse(course);
    enrollment.setEnrolledAt(LocalDateTime.now());
    enrollment.setCompletionPercentage(BigDecimal.ZERO);
    enrollment.setStatus("ACTIVE");

    return enrollmentRepository.save(enrollment);
  }

  public List<StudentCourseResponse> getStudentCourses(Long studentId) {
    List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

    return enrollments.stream().map(enrollment -> {
      StudentCourseResponse response = new StudentCourseResponse();
      response.setCourseId(enrollment.getCourse().getId());
      response.setTitle(enrollment.getCourse().getTitle());
      response.setDescription(enrollment.getCourse().getDescription());
      response.setThumbnailUrl(enrollment.getCourse().getThumbnailUrl());
      response.setCompletionPercentage(enrollment.getCompletionPercentage());
      response.setEnrollmentStatus(enrollment.getStatus());
      return response;
    }).collect(Collectors.toList());
  }

}
