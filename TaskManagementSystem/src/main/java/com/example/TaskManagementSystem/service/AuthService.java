package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.dto.RegisterRequest;
import com.example.TaskManagementSystem.entity.User;
import com.example.TaskManagementSystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;  //cong cu ma hoa

        public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
            this.userRepository = userRepository;
        }

        public String register(RegisterRequest request) {
            //kiem tra user da ton tai chua
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new RuntimeException("user da ton tai");
            }

            // tao entity user tu dto
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());

            //ma hoa mat khau truoc khi luu
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedPassword);

            //set quyen mac dinh la user
            user.setRoles(Collections.singleton("USER"));

            //luu Db
            userRepository.save(user);

            return "dang ky thanh cong <3";

        }
}
