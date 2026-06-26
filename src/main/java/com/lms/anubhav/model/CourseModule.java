package com.lms.anubhav.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_modules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Course course;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer sequenceNo;
}
