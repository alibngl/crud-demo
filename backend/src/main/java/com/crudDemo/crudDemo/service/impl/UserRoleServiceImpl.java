package com.crudDemo.crudDemo.service.impl;

import com.crudDemo.crudDemo.dao.UserRoleDao;
import com.crudDemo.crudDemo.dao.UserRoleRepository;
import com.crudDemo.crudDemo.model.UserRole;
import com.crudDemo.crudDemo.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);

    }

    @Override
    public void deleteUserRole(Long id) {
        userRoleRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<UserRole> userRoles) {
        userRoleDao.saveAll(userRoles);
    }
}
