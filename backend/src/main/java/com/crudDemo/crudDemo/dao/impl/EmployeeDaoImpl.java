package com.crudDemo.crudDemo.dao.impl;

import com.crudDemo.crudDemo.dao.EmployeeDao;
import com.crudDemo.crudDemo.model.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl extends AbstractDaoImpl implements EmployeeDao {

    @Override
    public Employee findById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        query.select(root).where(cb.equal(root.get("id"), id));
        Employee entity = entityManager.createQuery(query).getSingleResult();
        return entity.getId() !=null ? entity : null;
    }

    @Override
    public List<Employee> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        Predicate predicate = cb.isNotNull(root.get("id"));
        predicate = cb.and(predicate, cb.isTrue(root.get("enabled")));
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }
}
