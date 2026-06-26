package com.lms.anubhav.dto;

import lombok.Data;

@Data
public class CourseCreateRequest {
    private String title;
    private String description;
    private String courseCode;
    private Long teacherId;
}
