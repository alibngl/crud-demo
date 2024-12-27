package com.crudDemo.crudDemo.dao;

import com.crudDemo.crudDemo.model.RequestResponseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestResponseLogRepository extends JpaRepository<RequestResponseLog, Long> {
}
