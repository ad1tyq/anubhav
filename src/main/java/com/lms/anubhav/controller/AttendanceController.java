package com.lms.anubhav.controller;

import org.springframework.http.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lms.anubhav.dto.AttendanceRequest;
import com.lms.anubhav.model.Attendance;
import com.lms.anubhav.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AttendanceController {

  private final AttendanceService attendanceService;

  @PostMapping("/lessons/{lessonId}/attendance")
  public ResponseEntity<Attendance> markPresent(
      @PathVariable Long lessonId,
      @RequestBody AttendanceRequest request) {
    Attendance attendance = attendanceService.markAttendance(lessonId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
  }

  @PostMapping(value = "/lessons/{lessonId}/attendance/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> uploadAttendanceFile(
      @PathVariable Long lessonId,
      @RequestParam("file") MultipartFile file) {

    attendanceService.processCsvAttendance(lessonId, file);
    return ResponseEntity.ok("Attendance processed successfully");
  }

}
