package com.lms.anubhav.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ai_grading_breakdown")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiGradingBreakdown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grading_result_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AiGradingResult gradingResult;

    private String criterion;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    @Column(columnDefinition = "TEXT")
    private String feedback;
}
