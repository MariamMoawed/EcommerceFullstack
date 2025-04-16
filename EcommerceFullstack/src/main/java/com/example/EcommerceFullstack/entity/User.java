package com.example.EcommerceFullstack.entity;

import jakarta.persistence.*;

import javax.management.relation.Role;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
