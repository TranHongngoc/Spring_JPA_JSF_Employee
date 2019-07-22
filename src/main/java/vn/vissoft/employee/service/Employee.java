package vn.vissoft.employee.service;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employeesss")
@SequenceGenerator(name="seq")
public class Employee implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long id;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "manager")
    private String manager;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "work")
    private String work;

    @Column(name = "date_start")
    private String dateStart;

//    @ManyToOne
//    @JoinColumn(name = "group_id")
//    private Group group;


    public Employee() {
    }

    public Employee(String employeeCode, String name, String department, String manager, Double salary, String work, String dateStart) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.department = department;
        this.manager = manager;
        this.salary = salary;
        this.work = work;
        this.dateStart = dateStart;
       // this.group = group;
    }

    public Employee(int i, String name) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }
}
