package com.lms.anubhav.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.lms.anubhav.dto.AssignmentCreateRequest;
import com.lms.anubhav.model.Assignment;
import com.lms.anubhav.model.Course;
import com.lms.anubhav.model.User;
import com.lms.anubhav.repository.AssignmentRepository;
import com.lms.anubhav.repository.CourseRepository;
import com.lms.anubhav.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AssignmentService {

  private final AssignmentRepository assignmentRepository;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  @Transactional
  public Assignment createAssignment(Long courseId, AssignmentCreateRequest request) {

    Course course = courseRepository.findById(courseId)
        .orElseThrow(() -> new RuntimeException("Course not found"));

    User teacher = userRepository.findById(request.getTeacherId())
        .orElseThrow(() -> new RuntimeException("Teacher not found"));

    if (!course.getTeacher().getId().equals(teacher.getId())) {
      throw new RuntimeException("Unauthorized: Only the assigned teacher can create assignments for this course");
    }

    Assignment assignment = new Assignment();
    assignment.setCourse(course);
    assignment.setTitle(request.getTitle());
    assignment.setDescription(request.getDescription());
    assignment.setTotalMarks(request.getTotalMarks());
    assignment.setDueDate(request.getDueDate());
    assignment.setCreatedBy(teacher);

    return assignmentRepository.save(assignment);
  }
}
