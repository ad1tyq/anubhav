package com.lms.anubhav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.anubhav.model.Attendance;
import com.lms.anubhav.model.Lesson;
import com.lms.anubhav.model.User;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
  boolean existsByStudentAndLesson(User student, Lesson lesson);
}
