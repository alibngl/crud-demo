package com.crudDemo.crudDemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "request_response_log")
public class RequestResponseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "request_body", length = 5000)
    private String requestBody;

    @Column(name = "response_body", length = 5000)
    private String responseBody;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "completed_time")
    private LocalDateTime completedTime;

    @Column(name = "duration_ms")
    private Long durationMs;
}
