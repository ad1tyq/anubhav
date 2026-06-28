package com.lms.anubhav.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lms.anubhav.dto.AttendanceRequest;
import com.lms.anubhav.model.Attendance;
import com.lms.anubhav.model.Course;
import com.lms.anubhav.model.Lesson;
import com.lms.anubhav.model.User;
import com.lms.anubhav.repository.AttendanceRepository;
import com.lms.anubhav.repository.EnrollmentRepository;
import com.lms.anubhav.repository.LessonRepository;
import com.lms.anubhav.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {

  private final AttendanceRepository attendanceRepository;
  private final UserRepository userRepository;
  private final EnrollmentRepository enrollmentRepository;
  private final LessonRepository lessonRepository;

  @Transactional
  public Attendance markAttendance(Long lessonId, AttendanceRequest request) {

    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    User student = userRepository.findById(request.getStudentId())
        .orElseThrow(() -> new RuntimeException("Student not found"));

    Course course = lesson.getModule().getCourse();

    if (!enrollmentRepository.existsByStudentAndCourse(student, course)) {
      throw new RuntimeException("Unauthorized: Student is not enrolled in this course");
    }

    if (attendanceRepository.existsByStudentAndLesson(student, lesson)) {
      throw new RuntimeException("Attendance already recorded for this lesson");
    }

    Attendance attendance = new Attendance();
    attendance.setStudent(student);
    attendance.setLesson(lesson);
    attendance.setAttended(true);
    attendance.setAttendedAt(LocalDateTime.now());

    return attendanceRepository.save(attendance);

  }

  @Transactional
  public void processCsvAttendance(Long lessonId, MultipartFile file) {
    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new RuntimeException("Lesson not found"));

    System.out.println("Processing attendance for lesson: " + lessonId);
    
    // We try UTF_8 first as most users save standard CSVs as UTF-8,
    // but MS Teams exports are UTF-16. If this file was copy-pasted and saved, it's likely UTF-8.
    // To support both or debug, let's log the lines.
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_16))) {
      String line;
      boolean isDataSection = false;
      int linesRead = 0;

      while ((line = reader.readLine()) != null) {
        linesRead++;
        if (linesRead <= 5) {
            System.out.println("DEBUG Line " + linesRead + ": " + line);
        }

        // Skip the metadata at the top of the file
        if (line.startsWith("Name\t") || line.startsWith("Name,")) {
          isDataSection = true;
          System.out.println("DEBUG: Found Data Section header!");
          continue;
        }

        // Stop reading when we hit the summary activities at the bottom
        if (isDataSection && (line.startsWith("3. In-Meeting Activities") || line.trim().isEmpty())) {
          System.out.println("DEBUG: Reached end of data section.");
          break;
        }

        if (isDataSection) {
          // The file uses tabs, not commas, but let's support both for testing
          String delimiter = line.contains("\t") ? "\t" : ",";
          String[] columns = line.split(delimiter);

          // Column 0 is Name, Column 3 is In-Meeting Duration
          if (columns.length >= 4) {
            String rawName = columns[0].replace(" (Unverified)", "").trim();
            int durationMinutes = parseDuration(columns[3]);

            System.out.println("DEBUG: Parsed student '" + rawName + "' with duration: " + durationMinutes + "m");

            // Enforce 30-minute threshold
            if (durationMinutes >= 30) {
              String[] nameParts = rawName.split(" ", 2);
              String firstName = nameParts[0];
              String lastName = nameParts.length > 1 ? nameParts[1] : "";

              // Find student in database
              User student = userRepository.findByFirstNameAndLastName(firstName, lastName).orElse(null);

              if (student == null) {
                 System.out.println("DEBUG: Skipped saving because student '" + firstName + " " + lastName + "' was NOT found in the database.");
              } else if (attendanceRepository.existsByStudentAndLesson(student, lesson)) {
                 System.out.println("DEBUG: Skipped saving because student '" + firstName + " " + lastName + "' already has attendance marked.");
              } else {
                Attendance attendance = new Attendance();
                attendance.setStudent(student);
                attendance.setLesson(lesson);
                attendance.setAttended(true);
                attendance.setAttendedAt(LocalDateTime.now());
                attendanceRepository.save(attendance);
                System.out.println("DEBUG: Successfully saved attendance for " + firstName + " " + lastName);
              }
            } else {
              System.out.println("DEBUG: Skipped '" + rawName + "' because duration (" + durationMinutes + "m) is less than 30m.");
            }
          }
        }
      }
      System.out.println("DEBUG: Total lines read: " + linesRead);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse attendance file", e);
    }
  }

  // Helper method to convert "1h 6m 3s" or "55m 58s" into total minutes
  private int parseDuration(String durationStr) {
    if (durationStr == null || durationStr.isEmpty())
      return 0;
    int minutes = 0;
    try {
      if (durationStr.contains("h")) {
        String hStr = durationStr.substring(0, durationStr.indexOf("h")).trim();
        minutes += Integer.parseInt(hStr) * 60;
        durationStr = durationStr.substring(durationStr.indexOf("h") + 1);
      }
      if (durationStr.contains("m")) {
        String mStr = durationStr.substring(0, durationStr.indexOf("m")).trim();
        minutes += Integer.parseInt(mStr);
      }
    } catch (Exception e) {
      System.err.println("Error parsing duration: " + durationStr);
    }
    return minutes;
  }

}
