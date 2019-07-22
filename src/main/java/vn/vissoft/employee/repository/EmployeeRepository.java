package vn.vissoft.employee.repository;


import vn.vissoft.employee.service.Employee;

import java.util.List;


public interface EmployeeRepository {

    List findAll();

    Employee findById(Long id);

    List findByName(String name, String employeeCode, String department, Double salaryFrom, Double salaryTo);

    void create(Employee employee);

    void delete(Employee employee);

    void update(Employee employee);
}
