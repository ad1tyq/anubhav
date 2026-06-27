package com.lms.anubhav.service;

import org.springframework.stereotype.Service;

import com.lms.anubhav.dto.LessonCreateRequest;
import com.lms.anubhav.dto.ModuleCreateRequest;
import com.lms.anubhav.model.Course;
import com.lms.anubhav.model.CourseModule;
import com.lms.anubhav.model.Lesson;
import com.lms.anubhav.repository.CourseModuleRepository;
import com.lms.anubhav.repository.CourseRepository;
import com.lms.anubhav.repository.LessonRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseContentService {
  private final CourseRepository courseRepository;
  private final CourseModuleRepository moduleRepository;
  private final LessonRepository lessonRepository;

  @Transactional
  public CourseModule createModule(Long courseId, ModuleCreateRequest request) {
    Course course = courseRepository.findById(courseId)
        .orElseThrow(() -> new RuntimeException("Course not found"));

    CourseModule module = new CourseModule();
    module.setCourse(course);
    module.setTitle(request.getTitle());
    module.setDescription(request.getDescription());
    module.setSequenceNo(request.getSequenceNo());

    return moduleRepository.save(module);
  }

  @Transactional
  public Lesson createLesson(Long moduleId, LessonCreateRequest request) {
    CourseModule module = moduleRepository.findById(moduleId)
        .orElseThrow(() -> new RuntimeException("Module not found"));

    Lesson lesson = new Lesson();
    lesson.setModule(module);
    lesson.setTitle(request.getTitle());
    lesson.setLessonType(request.getLessonType());
    lesson.setVideoUrl(request.getVideoUrl());
    lesson.setContent(request.getContent());
    lesson.setDurationMinutes(request.getDurationMinutes());
    lesson.setSequenceNo(request.getSequenceNo());

    return lessonRepository.save(lesson);
  }

}
