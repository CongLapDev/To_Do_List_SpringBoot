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

    //Sua Task
    public Task updateTask(Long id, Task taskRequest) {
        //tim task theo id
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task khong ton tai"));

        //lay user hien tai
        User currentUser = getCurrentUser(); // lay user hien tai dang dang nhap gan vao currentUser

        //Kiem tra quyen so huu, id dang nhap khac id nguoi tao task -> block
        if(!task.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Ban khong co quyen sua task nay");
        }
        //cap nhat thong tin
        task.setTitle(taskRequest.getTitle());
        task.setDescription(task.getDescription());
        task.setStatus(task.getStatus());

        return taskRepository.save(task);
    }

    //Xoa Task
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task khong ton tai"));

        User currentUser = getCurrentUser();

        //Kiem tra quyen so huu
        if(!task.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Ban khong co quyen xoa task nay");
        }
        taskRepository.delete(task);
    }

}
