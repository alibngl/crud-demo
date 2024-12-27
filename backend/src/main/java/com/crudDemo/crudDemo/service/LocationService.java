package com.crudDemo.crudDemo.service;

import com.crudDemo.crudDemo.model.Location;

public interface LocationService {

    void save(Location location);

    void updateLocation(Long id, String location);

    Location findById(Long id);
}
