package com.lms.anubhav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.anubhav.model.Course;
import com.lms.anubhav.model.Enrollment;
import com.lms.anubhav.model.User;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

  boolean existsByStudentAndCourse(User student, Course course);

  List<Enrollment> findByStudentId(Long studentId);
}
