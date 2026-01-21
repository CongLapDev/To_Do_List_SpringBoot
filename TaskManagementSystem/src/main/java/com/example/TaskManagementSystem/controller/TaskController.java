package com.example.TaskManagementSystem.controller;

import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //danh dau day la class chuyen phuc vu api tra ve json
@RequestMapping("/api/tasks") //duong dan

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //api tao task moi, method post
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        //requestbody se lay json tu request chuyen thanh object java
        return taskService.createTask(task);
    }

    //lay tat ca cac task
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    //api sua task api/task/{id}
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    // Xoa Task
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "Xoa thanh cong task id: " + id;
    }
}
