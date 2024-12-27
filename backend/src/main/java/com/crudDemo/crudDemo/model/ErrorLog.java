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
@Table(name = "error_log")
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "layer_name")
    private String layerName;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "exception_message", length = 2500)
    private String exceptionMessage;

    @Column(name = "stack_trace", length = 2500)
    private String stackTrace;

    @Column(name = "created_time")
    private LocalDateTime createdTime;
}
