package com.example.TaskManagementSystem.config;

import com.example.TaskManagementSystem.entity.User;
import com.example.TaskManagementSystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import  java.util.Collections;

@Component
public class DataSeeder implements CommandLineRunner{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        //kiem tra xem co admin chua, chua co thi tao
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@gmail.com");
            admin.setRoles(Collections.singleton("ADMIN")); //gan quyen admin

            userRepository.save(admin);
            System.out.println("da tao tai khoan admin mau: admin/admin123");
        }
    }
}
