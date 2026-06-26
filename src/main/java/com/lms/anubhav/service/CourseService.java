package com.lms.anubhav.service;

import com.lms.anubhav.dto.CourseCreateRequest;
import com.lms.anubhav.dto.CourseResponse;
import com.lms.anubhav.model.Course;
import com.lms.anubhav.model.User;
import com.lms.anubhav.repository.CourseRepository;
import com.lms.anubhav.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public CourseResponse createCourse(CourseCreateRequest request) {
        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCourseCode(request.getCourseCode());
        course.setTeacher(teacher);
        course.setStatus("DRAFT");
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());

        Course savedCourse = courseRepository.save(course);
        return mapToResponse(savedCourse);
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CourseResponse mapToResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setCourseCode(course.getCourseCode());
        response.setStatus(course.getStatus());
        if (course.getTeacher() != null) {
            response.setTeacherId(course.getTeacher().getId());
            response.setTeacherName(course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName());
        }
        response.setCreatedAt(course.getCreatedAt());
        return response;
    }
}
