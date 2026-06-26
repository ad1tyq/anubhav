package com.lms.anubhav.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String courseCode;
    private String status;
    private Long teacherId;
    private String teacherName;
    private LocalDateTime createdAt;
}
