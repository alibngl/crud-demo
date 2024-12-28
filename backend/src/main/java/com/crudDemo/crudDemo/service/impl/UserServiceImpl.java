package com.crudDemo.crudDemo.service.impl;

import com.crudDemo.crudDemo.controller.UserController;
import com.crudDemo.crudDemo.dao.UserDao;
import com.crudDemo.crudDemo.dao.UserRepository;
import com.crudDemo.crudDemo.enums.UserRoleEnum;
import com.crudDemo.crudDemo.model.User;
import com.crudDemo.crudDemo.model.UserRole;
import com.crudDemo.crudDemo.model.dto.UserDTO;
import com.crudDemo.crudDemo.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void save(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("password cannot be null or empty");
        }
        String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        for (UserRole userRoleTable : user.getUserRoles()) {
            userRoleTable.setUser(user);
        }
        userDao.save(user);
    }

    @Transactional
    @Override
    @CacheEvict(value = "users", key = "#id")
    public void updateUser(Long id, UserDTO userDTO) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        List<String> userRoleValues = user.getUserRoles().stream()
                .map(userRole -> userRole.getUserRoleEnum().getValue())
                .toList();

        if (userDTO.getRoles().containsAll(userRoleValues)) {
            user.getUserRoles().clear();
            for (String role : userDTO.getRoles()) {
                UserRole userRole = new UserRole();
                userRole.setUserRoleEnum(UserRoleEnum.valueOf(role));
                userRole.setUser(user);
                user.getUserRoles().add(userRole);
            }
        }
        user.setEnabled(userDTO.isEnabled());
        userDao.update(user);
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public User getById(Long id) {
        LOG.info("getById: " + id);
        User user;
        user = userDao.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        return user;
    }

    @Override
    public User getByUsername(String username) {
        User userTable;
        userTable = userDao.findByUsername(username);
        if (userTable == null) {
            throw new RuntimeException("User not found with ID: " + username);
        }
        return userTable;
    }

    @Override
    public List<User> getAllUsers() { // bu method ile 10 11 ms sürüyor. dao kısmında criteria Api bu kadar sürdü. sql sorgusu ile 200 ms üzeri sürüyor
        return userDao.findAll();
    }

//    @Override
//    public List<UserTable> getAllUsers() { // bu method ile 30 ms sürüyor
//        return userRepository.findAll();
//    }


    @Transactional
    @Override
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        User deleteUser = userDao.findById(id);
        if (deleteUser != null) {
            userDao.delete(deleteUser);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Kullanıcı adı kontrol ediliyor: " + username);

        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + username);
        }
        System.out.println("Kullanıcı bulundu: " + user.getUsername());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getUserRoles().stream()
                        .map(UserRole::getUserRoleEnum)
                        .collect(Collectors.toList())
        );
    }
}
