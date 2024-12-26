package com.crudDemo.crudDemo.dao.impl;

import com.crudDemo.crudDemo.dao.AbstractDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;

public class AbstractDaoImpl implements AbstractDao, Serializable {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public <T> void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public <T> void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public <T> void delete(T entity) {
        entityManager.remove(entity);
    }
}
