package com.example.TaskManagementSystem.entity;

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
}

