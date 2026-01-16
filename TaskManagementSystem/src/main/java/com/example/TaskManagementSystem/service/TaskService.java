package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  //danh dau day la banh rang service

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //ham tao task
    public Task createTask(Task task) {
        return taskRepository.save(task); //luu DB
    }

    //ham lay tat ca task
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); //lay het tu DB
    }

}
