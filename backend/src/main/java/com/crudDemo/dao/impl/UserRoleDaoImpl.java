package com.crudDemo.crudDemo.dao.impl;

import com.crudDemo.crudDemo.dao.UserRoleDao;
import com.crudDemo.crudDemo.model.UserRole;
import com.crudDemo.crudDemo.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class UserRoleDaoImpl extends AbstractDaoImpl implements UserRoleDao {

    @Override
    public UserRole findRolesByUserId(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserRole> query = cb.createQuery(UserRole.class);
        Root<UserRole> userRoleRoot = query.from(UserRole.class);
        Join<UserRole, User> userJoin = userRoleRoot.join("userTable");

        query.select(userRoleRoot)
                .where(cb.equal(userJoin.get("id"), userId));

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public void deleteUserRole(Long userRoleId) {
        UserRole existingRole = entityManager.createQuery(
                        "select u from UserRole u where u.id = :id", UserRole.class)
                .setParameter("id", userRoleId)
                .getSingleResult();

        if (existingRole != null) {
            entityManager.remove(existingRole);
        }
    }
}
