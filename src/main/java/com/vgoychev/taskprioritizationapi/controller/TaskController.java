package com.vgoychev.taskprioritizationapi.controller;

import com.vgoychev.taskprioritizationapi.entity.Task;
import com.vgoychev.taskprioritizationapi.entity.dto.TaskDto;
import com.vgoychev.taskprioritizationapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create-task")
    public ResponseEntity<Void> createTask(@Validated @RequestBody TaskDto taskDto, UriComponentsBuilder uriComponentsBuilder){
        Task task = taskService.createTask(taskDto);
        return ResponseEntity.created(uriComponentsBuilder.path("/create-task/{taskID}").buildAndExpand(task.getId()).toUri()).build();
    }

    @GetMapping("/get-task")
    public ResponseEntity<Void> getTask(){
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/sorted-by-priority")
    public ResponseEntity<List<Task>> getSortedByPriorityTasks() {
        List<Task> tasks = taskService.getTasksSortedByPriority();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/sorted-by-date")
    public ResponseEntity<List<Task>> getSortedByDueDateTasks() {
        List<Task> tasks = taskService.getTasksSortedByDueDate();
        return  ResponseEntity.ok(tasks);
    }

    @GetMapping("/filter-completion")
    public ResponseEntity<List<Task>> getFilterByCompletion(){
        List<Task> tasks = taskService.getTasksFilteredByCompletion();
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/delete/{taskID}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable("taskID") String taskId){
       taskService.deleteTaskById(taskId);
       return ResponseEntity.ok().build();
    }

    @PutMapping("/{taskID}")
    public ResponseEntity<Task> updateTaskById(@PathVariable("taskID") String taskId, @Validated @RequestBody TaskDto taskDto){

        return ResponseEntity.ok(taskService.updateTask(taskId, taskDto));
    }


}
