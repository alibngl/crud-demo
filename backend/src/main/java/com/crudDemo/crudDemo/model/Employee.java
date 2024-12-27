package com.crudDemo.crudDemo.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@Table(name = "employee_table")
@ToString(exclude = "user")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 200, nullable = false)
    private String firstName;

    @Column(name = "surname", length = 200, nullable = false)
    private String surName;

    @Column(name = "tckn", length = 11)
    private String tckn;

    @Column(name = "manager")
    private boolean manager;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_fk")
    private Location location;
}
