package com.crudDemo.crudDemo.dao;

import com.crudDemo.crudDemo.model.Location;
import com.crudDemo.crudDemo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationDao extends AbstractDao {

  Location findById(Long id);
}