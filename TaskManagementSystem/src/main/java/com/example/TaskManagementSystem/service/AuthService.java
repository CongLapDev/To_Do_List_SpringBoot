package com.example.TaskManagementSystem.service;

import com.example.TaskManagementSystem.dto.LoginRequest;
import com.example.TaskManagementSystem.dto.RegisterRequest;
import com.example.TaskManagementSystem.entity.User;
import com.example.TaskManagementSystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.TaskManagementSystem.security.JwtTokenProvider;

import java.util.Collections;

@Service
public class AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;  //cong cu ma hoa
        private final JwtTokenProvider jwtTokenProvider;

        public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
            this.passwordEncoder = passwordEncoder;
            this.userRepository = userRepository;
            this.jwtTokenProvider = jwtTokenProvider;
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

        //ham login
    public String login(LoginRequest request) {
        // tim user
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User khong ton tai"));

        //kiem tra mat khau matches theo thu tu ( mk nhap vao , mat khau ma hoa)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mat khau sai");
        }
        // tao va tra ve Token
        return jwtTokenProvider.generateToken(user.getUsername());
    }

}
