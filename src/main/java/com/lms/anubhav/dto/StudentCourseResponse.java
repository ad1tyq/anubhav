package com.lms.anubhav.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class StudentCourseResponse {
  private Long courseId;
  private String title;
  private String description;
  private String thumbnailUrl;
  private BigDecimal completionPercentage;
  private String enrollmentStatus;
}
