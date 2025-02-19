package com.vgoychev.taskprioritizationapi.service;

import com.vgoychev.taskprioritizationapi.entity.Task;
import com.vgoychev.taskprioritizationapi.entity.dto.TaskDto;

import java.util.List;

public interface TaskService {

    Task createTask(TaskDto taskDto);

    List<Task> getAllTasks();

    List<Task> getTasksSortedByPriority();

    List<Task> getTasksSortedByDueDate();

    List<Task> getTasksFilteredByCompletion();

    void deleteTaskById(String taskId);

    Task updateTask(String taskId, TaskDto taskDto);
}
