package com.lms.anubhav.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.anubhav.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
  List<Lesson> findByModuleIdOrderBySequenceNoAsc(Long moduleId);
}
