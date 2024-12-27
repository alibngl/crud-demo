package com.crudDemo.crudDemo.dao;

import java.util.List;

public interface AbstractDao {
    <T> void delete(T object);

    <T> void save(T entity);

    <T> void update(T entity);

    <T> void saveAll(List<T> entities);
}
