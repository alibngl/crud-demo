package com.crudDemo.crudDemo.dao.impl;

import com.crudDemo.crudDemo.dao.LocationDao;
import com.crudDemo.crudDemo.model.Location;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationDaoImpl extends AbstractDaoImpl implements LocationDao {

    @Override
    public Location findById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> query = cb.createQuery(Location.class);
        Root<Location> root = query.from(Location.class);
        query.select(root).where(cb.equal(root.get("id"), id));
        List<Location> entity = entityManager.createQuery(query).getResultList();
        return entity.isEmpty() ? null : entity.get(0);
    }
}
