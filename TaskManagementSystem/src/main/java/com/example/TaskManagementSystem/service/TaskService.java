package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.entity.User;
import com.example.TaskManagementSystem.repository.TaskRepository;
import com.example.TaskManagementSystem.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  //danh dau day la banh rang service

public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    //ham lay user dang login hien tai
    private User getCurrentUser() {
        //lay username tu SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    //ham tao task gan cho user hien tai
    public Task createTask(Task task) {
        User user = getCurrentUser(); //ai dang goi ham nay
        task.setUser(user); //gan task nay cho nguoi do
        return taskRepository.save(task);
    } //luu DB

    //ham lay tat ca task
    public List<Task> getAllTasks() {
        User user = getCurrentUser();
        return taskRepository.findByUser(user); //chi lay task cua ho
    }   //lay het tu DB

}
