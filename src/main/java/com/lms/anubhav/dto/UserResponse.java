package com.lms.anubhav.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicture;
    private String status;
    private java.util.List<String> roles;
    private LocalDateTime createdAt;
}
