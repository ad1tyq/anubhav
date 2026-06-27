package com.lms.anubhav.dto;

import lombok.Data;

@Data
public class ModuleCreateRequest {
  private String title;
  private String description;
  private Integer sequenceNo;
}
