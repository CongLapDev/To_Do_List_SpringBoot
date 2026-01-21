package com.example.TaskManagementSystem.entity;

import com.example.TaskManagementSystem.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks") //ten bang trong db
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tu tang id
    private Long id;
    private String title;
    private String description;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY) //nhieu task thuoc ve 1 user, lazy de toi uu hieu nang
    @JoinColumn(name = "user_id") //cho cot id trong bang task lam khoa ngoai
    @com.fasterxml.jackson.annotation.JsonIgnore //chi in ra id va thong tin task, khong in ra thong tin user
    private User user;
}

