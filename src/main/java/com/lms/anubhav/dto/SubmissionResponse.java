package com.lms.anubhav.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SubmissionResponse {
  private Long submissionId;
  private Long studentId;
  private String studentName; // Useful for the dashboard
  private String submissionUrl;
  private LocalDateTime submittedAt;
  private String status;
  private BigDecimal marksObtained;
  private String feedback;
}
