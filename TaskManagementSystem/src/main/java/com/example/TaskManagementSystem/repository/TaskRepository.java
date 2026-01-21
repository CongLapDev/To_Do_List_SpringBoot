package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.entity.Task;
import com.example.TaskManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository



public interface TaskRepository extends JpaRepository<Task, Long>{
    //doan nay spring data JPA dich thanh "select * from tasks where user_id = ? "
    List<Task> findByUser(User user);
}
