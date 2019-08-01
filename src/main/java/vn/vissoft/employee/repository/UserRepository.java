package vn.vissoft.employee.repository;

import vn.vissoft.employee.model.User;


import java.util.List;

public interface UserRepository {

    List findAll();

    List findNames();

    User findById(Long id);
    User findByName(String name);

    void create(User user);

    void delete(User user);

    void update(User user);

    User findByUsername(String username);
}
