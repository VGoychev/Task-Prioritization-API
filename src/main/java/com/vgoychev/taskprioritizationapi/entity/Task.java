package com.vgoychev.taskprioritizationapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "task")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    @Enumerated(value = EnumType.STRING)
    private Priority priority;

    @Column(name = "date")
    private LocalDate dueDate;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "is_urgent")
    private boolean isUrgent;

    @Column(name = "is_critical")
    private boolean isCritical;

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
