package com.example.TaskManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) //username khong duoc trung nhau
    private String username;

    @Column(nullable = false)
    private String password; //luu mat khau da ma hoa khong luu text

    private String email;

    //1 user co the co 2 role,  fetchtype.eager khi lay user lay luon ca role di kem
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();
}
