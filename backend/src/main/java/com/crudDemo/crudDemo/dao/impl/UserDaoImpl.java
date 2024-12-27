package com.crudDemo.crudDemo.dao.impl;

import com.crudDemo.crudDemo.dao.UserDao;
import com.crudDemo.crudDemo.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends AbstractDaoImpl implements UserDao {

    @Override
    public User findById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.equal(root.get("id"), id));
        List<User> entity = entityManager.createQuery(query).getResultList();
        return entity.isEmpty() ? null : entity.get(0);
    }

    @Override
    public User findByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.equal(root.get("username"), username));
        List<User> entity = entityManager.createQuery(query).getResultList();
        return entity.isEmpty() ? null : entity.get(0);
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate predicate = cb.isNotNull(root.get("id"));
        predicate = cb.and(predicate, cb.isTrue(root.get("enabled")));
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

//    @Override
//    public List<UserTable> findAll() {
//        String jpql = "SELECT u FROM UserTable u WHERE u.id IS NOT NULL";
//        return entityManager.createQuery(jpql, UserTable.class).getResultList();
//    }
}