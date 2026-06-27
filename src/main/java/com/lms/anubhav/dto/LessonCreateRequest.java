package com.lms.anubhav.dto;

import lombok.Data;

@Data
public class LessonCreateRequest {
  private String title;
  private String lessonType;
  private String videoUrl;
  private String content;
  private Integer durationMinutes;
  private Integer sequenceNo;
}
