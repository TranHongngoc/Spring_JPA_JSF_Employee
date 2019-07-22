package vn.vissoft.employee.repository;

import vn.vissoft.employee.model.User;


import java.util.List;

public interface UserRepository {

    List findAll();

    User findById(Long id);

    void create(User user);

    void delete(User user);

    void update(User user);
}
