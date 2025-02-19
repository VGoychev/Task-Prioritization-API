package com.vgoychev.taskprioritizationapi.service.impl;

import com.vgoychev.taskprioritizationapi.entity.Priority;
import com.vgoychev.taskprioritizationapi.entity.Task;
import com.vgoychev.taskprioritizationapi.entity.dto.TaskDto;
import com.vgoychev.taskprioritizationapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setPriority(Priority.MEDIUM);
        task.setCompleted(false);
        task.setCritical(false);
        task.setUrgent(false);

        taskDto = new TaskDto();
        taskDto.setTitle("Test Task");
        taskDto.setDueDate(LocalDate.now().plusDays(5));
        taskDto.setIsCompleted(false);
        taskDto.setIsCritical(false);
        taskDto.setIsUrgent(false);
    }

    @Test
    void testCreateTask() {
        when(modelMapper.map(taskDto, Task.class)).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(taskDto);

        assertNotNull(createdTask);
        assertEquals(task.getTitle(), createdTask.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTasksSortedByPriority() {
        Task highPriorityTask = new Task();
        highPriorityTask.setPriority(Priority.HIGH);
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task, highPriorityTask));

        List<Task> sortedTasks = taskService.getTasksSortedByPriority();

        assertEquals(Priority.HIGH, sortedTasks.get(0).getPriority());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTasksSortedByDueDate() {
        when(taskRepository.findAllByOrderByDueDateAsc()).thenReturn(Arrays.asList(task));

        List<Task> tasks = taskService.getTasksSortedByDueDate();

        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAllByOrderByDueDateAsc();
    }

    @Test
    void testGetTasksFilteredByCompletion() {
        when(taskRepository.findAllByCompletedIsTrue()).thenReturn(Arrays.asList(task));

        List<Task> completedTasks = taskService.getTasksFilteredByCompletion();

        assertEquals(1, completedTasks.size());
        verify(taskRepository, times(1)).findAllByCompletedIsTrue();
    }

    @Test
    void testDeleteTaskById() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTaskById("1");

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTask("1", taskDto);

        assertNotNull(updatedTask);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.updateTask("1", taskDto));
    }
}