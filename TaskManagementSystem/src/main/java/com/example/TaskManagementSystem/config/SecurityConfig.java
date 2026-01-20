package com.example.TaskManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.TaskManagementSystem.security.JwtAuthenticationFiler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration //bao cho spring day la file cau hinh
@EnableWebSecurity //kich hoat tinh nang bao mat web

public class SecurityConfig {

    private final JwtAuthenticationFiler jwtAuthenticationFiler;

    public SecurityConfig(JwtAuthenticationFiler jwtAuthenticationFiler) {
        this.jwtAuthenticationFiler = jwtAuthenticationFiler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                //cau hinh quyen truy cap
                .authorizeHttpRequests(auth -> auth
                //cho phep tat ca cac request truy cap ma khong can dang nhap
                                .requestMatchers("/h2-console/**",
                                                            "/api/auth/**").permitAll()
                        //tat ca cac request con lai bat buoc pahi co token
                                .anyRequest().authenticated()
                )
        .addFilterBefore(jwtAuthenticationFiler, UsernamePasswordAuthenticationFilter.class);
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        //tat xframe neu khong spring security chan
        return http.build();


    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
