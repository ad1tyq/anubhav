package com.lms.anubhav.dto;

import lombok.Data;

@Data
public class SubmissionRequest {
  private Long studentId;
  private String submissionUrl;
}
