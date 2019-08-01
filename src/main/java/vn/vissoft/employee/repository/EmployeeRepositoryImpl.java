package vn.vissoft.employee.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.vissoft.employee.service.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<Employee> findAll() {

        Query query = entityManager.createQuery("select e FROM Employee e");
        return query.getResultList();

    }


    @Override
    public Employee findById(Long id) {
        Query query = entityManager.createQuery("select e FROM Employee e WHERE e.id= :id");
        query.setParameter("id", id);
        return (Employee) query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Employee> findByName(String name, String employeeCode, String department, Double salaryFrom, Double salaryTo) {

        String sql = "select e from Employee e where 1=1 ";

        if (!"".equals(name)) {
            sql = sql + " and (e.name like:name)";
        }

        if (!"".equals(employeeCode)) {
            sql = sql + " and (e.employeeCode like:employeeCode)";
        }

        if (!"".equals(department)) {
            sql = sql + " and (e.department like:department)";
        }

        if (salaryFrom != 0) {
            sql = sql + " and (e.salary >:salaryFrom )";
        }
        if (salaryTo != 0) {
            sql = sql + " and (e.salary <:salaryTo )";
        }

        Query query = entityManager.createQuery(sql);

        if (!"".equals(name)) {
            query.setParameter("name", "%" + name + "%");
        }
        if (!"".equals(employeeCode)) {
            query.setParameter("employeeCode", "%" + employeeCode + "%");
        }
        if (!"".equals(department)) {
            query.setParameter("department", "%" + department + "%");
        }

        if (salaryFrom != 0) {
            query.setParameter("salaryFrom", salaryFrom);

        }
        if (salaryTo != 0) {
            query.setParameter("salaryTo", salaryTo);

        }

        return query.getResultList();
    }

    @Override
    @Transactional
    public void create(Employee employee) {
        this.entityManager.persist(employee);

    }


    @Override
    public void update(Employee employee) {
        entityManager.merge(employee);

    }

    @Override
    public void delete(Employee employee) {
        entityManager.remove(entityManager.merge(employee));

    }
}
