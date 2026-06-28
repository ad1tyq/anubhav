package com.lms.anubhav.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GradeRequest {
    private BigDecimal marksObtained;
    private String feedback;
    private Long teacherId; 
}
