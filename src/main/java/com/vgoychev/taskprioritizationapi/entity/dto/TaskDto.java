package com.vgoychev.taskprioritizationapi.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @NotNull(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Description not found")
    private String description;

    @NotNull(message = "Date not found")
    private LocalDate dueDate;

    @NotNull(message = "isCompleted cannot be null")
    private Boolean isCompleted;

    @NotNull(message = "isUrgent cannot be null")
    private Boolean isUrgent;

    @NotNull(message = "isCritical cannot be null")
    private Boolean isCritical;

}
