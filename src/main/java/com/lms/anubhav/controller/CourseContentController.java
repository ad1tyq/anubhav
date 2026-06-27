package com.lms.anubhav.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.anubhav.dto.LessonCreateRequest;
import com.lms.anubhav.dto.ModuleCreateRequest;
import com.lms.anubhav.model.CourseModule;
import com.lms.anubhav.model.Lesson;
import com.lms.anubhav.service.CourseContentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseContentController {

  private final CourseContentService contentService;

  @PostMapping("/courses/{courseId}/modules")
  public ResponseEntity<CourseModule> addModule(
      @PathVariable long courseId,
      @RequestBody ModuleCreateRequest request) {
    CourseModule createModule = contentService.createModule(courseId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(createModule);
  }

  @PostMapping("/modules/{moduleId}/lessons")
  public ResponseEntity<Lesson> addLesson(
      @PathVariable Long moduleId,
      @RequestBody LessonCreateRequest request) {
    Lesson createdLesson = contentService.createLesson(moduleId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdLesson);
  }

}
