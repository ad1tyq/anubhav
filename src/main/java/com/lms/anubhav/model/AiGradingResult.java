package com.lms.anubhav.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_grading_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiGradingResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AssignmentSubmission submission;

    @Column(precision = 5, scale = 2)
    private BigDecimal overallScore;

    @Column(columnDefinition = "TEXT")
    private String aiFeedback;

    @Column(precision = 5, scale = 2)
    private BigDecimal plagiarismScore;

    private LocalDateTime gradedAt;
    private String modelName;
}
