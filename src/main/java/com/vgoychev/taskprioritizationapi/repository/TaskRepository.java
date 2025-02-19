package com.vgoychev.taskprioritizationapi.repository;

import com.vgoychev.taskprioritizationapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findFirstById(Long id);

    List<Task> getAllByDueDateAfter(LocalDate dueDateBefore);

    List<Task> findAll();

    List<Task> findAllByOrderByDueDateAsc();


    @Query("SELECT t FROM Task t WHERE t.isCompleted = true")
    List<Task> findAllByCompletedIsTrue();


}
