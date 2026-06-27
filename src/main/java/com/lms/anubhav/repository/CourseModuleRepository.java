package com.lms.anubhav.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.anubhav.model.CourseModule;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
  List<CourseModule> findByCourseIdOrderBySequenceNoAsc(Long courseId);
}
