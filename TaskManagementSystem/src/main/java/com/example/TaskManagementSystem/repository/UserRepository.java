package com.example.TaskManagementSystem.repository;

import com.example.TaskManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

    //kiem tra user ton tai chua khi dang ki
    Boolean existsByUsername(String username);

}
