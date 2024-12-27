package com.crudDemo.crudDemo.service.impl;

import com.crudDemo.crudDemo.dao.LocationDao;
import com.crudDemo.crudDemo.model.Location;
import com.crudDemo.crudDemo.service.LocationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Transactional
    @Override
    public void save(Location location) {
        locationDao.save(location);
    }

    @Transactional
    @Override
    public void updateLocation(Long id, String location) {
        Location l = locationDao.findById(id);
        l.setLocation(location);
        locationDao.save(l);
    }

    @Override
    public Location findById(Long id) {
        return locationDao.findById(id);
    }
}
