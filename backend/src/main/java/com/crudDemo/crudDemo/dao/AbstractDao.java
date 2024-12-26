package com.crudDemo.crudDemo.dao;

public interface AbstractDao {
    <T> void delete(T object);

    <T> void save(T entity);

    <T> void update(T entity);
}
