package com.example.TaskManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;


@Configuration //bao cho spring day la file cau hinh
@EnableWebSecurity //kich hoat tinh nang bao mat web

public class SercurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                //cau hinh quyen truy cap
                .authorizeHttpRequests(auth -> auth
                //cho phep tat ca cac request truy cap ma khong can dang nhap
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().permitAll()
                )
        .headers(headers -> headers.frameOptions(frame -> frame
                .sameOrigin()) );
        return http.build();


    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
