package com.vgoychev.taskprioritizationapi.service.impl;

import com.vgoychev.taskprioritizationapi.entity.Priority;
import com.vgoychev.taskprioritizationapi.entity.Task;
import com.vgoychev.taskprioritizationapi.entity.dto.TaskDto;
import com.vgoychev.taskprioritizationapi.repository.TaskRepository;
import com.vgoychev.taskprioritizationapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Override
    public Task createTask(TaskDto taskDto) {
        Task map = this.modelMapper.map(taskDto, Task.class);
        map.setPriority(calculatePriority(
                taskDto.getIsCritical(),
                taskDto.getIsUrgent(),
                taskDto.getIsCompleted(),
                taskDto.getDueDate()
        ));
        return this.taskRepository.save(map);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksSortedByPriority(){
        List<Task> tasks = taskRepository.findAll();

        tasks.sort(Comparator
                .comparing(Task::getPriority, Comparator.reverseOrder())
                .thenComparing(Task::getDueDate));

        return tasks;
    }

    @Override
    public List<Task> getTasksSortedByDueDate() {
        return taskRepository.findAllByOrderByDueDateAsc();
    }

    @Override
    public List<Task> getTasksFilteredByCompletion() {
        return taskRepository.findAllByCompletedIsTrue();
    }

    @Override
    public void deleteTaskById(String taskId) {
        taskRepository.deleteById(Long.parseLong(taskId));
    }

    @Override
    public Task updateTask(String taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(Long.parseLong(taskId))
                .orElseThrow(() -> new EntityNotFoundException("Task with id " + taskId + " not found"));

        if (taskDto.getTitle() != null && !Objects.equals(task.getTitle(), taskDto.getTitle())) {
            task.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null && !Objects.equals(task.getDescription(), taskDto.getDescription())) {
            task.setDescription(taskDto.getDescription());
        }
        if (taskDto.getDueDate() != null && !Objects.equals(task.getDueDate(), taskDto.getDueDate())) {
            task.setDueDate(taskDto.getDueDate());
        }
        if (taskDto.getIsCompleted() != null && !Objects.equals(task.isCompleted(), taskDto.getIsCompleted())) {
            task.setCompleted(taskDto.getIsCompleted());
        }
        if (taskDto.getIsUrgent() != null && !Objects.equals(task.isUrgent(), taskDto.getIsUrgent())) {
            task.setUrgent(taskDto.getIsUrgent());
        }
        if (taskDto.getIsCritical() != null && !Objects.equals(task.isCritical(), taskDto.getIsCritical())) {
            task.setCritical(taskDto.getIsCritical());
        }

        return taskRepository.save(task);
    }

    private Priority calculatePriority(boolean isCritical, boolean isUrgent, boolean isCompleted, LocalDate dueDate) {

        List<Task> laterTasks = taskRepository.getAllByDueDateAfter(dueDate);

        if (isCompleted){
            return Priority.LOW;
        }

        if (laterTasks.isEmpty()){
            return Priority.HIGH;
        }

        if (isCritical || isUrgent){
            return  Priority.HIGH;
        }

        return Priority.MEDIUM;
    }



}
