package com.crudDemo.crudDemo.dao;

import com.crudDemo.crudDemo.model.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}
