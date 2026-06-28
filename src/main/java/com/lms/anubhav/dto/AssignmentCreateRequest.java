package com.lms.anubhav.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AssignmentCreateRequest {
  private String title;
  private String description;
  private Integer totalMarks;
  private LocalDateTime dueDate;
  private Long teacherId;
}
