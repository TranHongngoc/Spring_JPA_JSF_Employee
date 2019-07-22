package vn.vissoft.employee.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(Long id);

    List findByName(String name, String employeeCode, String department, Double salaryFrom, Double salaryTo);

    void create(Employee employee);

    void update(Employee employee);

    void delete(Employee employee);
}
