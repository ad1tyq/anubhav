package com.lms.anubhav.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "grading_rubrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradingRubric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Assignment assignment;

    private String criterion;
    private Integer maxMarks;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;
}
