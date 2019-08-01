package vn.vissoft.employee.service;

import org.springframework.stereotype.Service;
import vn.vissoft.employee.model.User;

import java.util.List;

@Service
public interface UserService {

    List<User> findAll();

    List<String> findNames();

    User findById(Long id);
    User findByName(String name);

    void create(User user);

    void update(User user);

    void delete(User user);
}
