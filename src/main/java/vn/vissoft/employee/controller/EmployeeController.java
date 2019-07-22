package vn.vissoft.employee.controller;



import com.sun.javafx.scene.control.skin.Utils;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import viettel.passport.client.VSATransport;
import viettel.passport.client.VSAValidate;
import vn.vissoft.employee.model.User;
import vn.vissoft.employee.service.Employee;
import vn.vissoft.employee.service.EmployeeService;
import vn.vissoft.employee.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@SessionScoped
//@ManagedBean(name = "employeeController")
@Named
@Join(path = "/", to = ("/employee-list.jsf"))
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserService userService;

    private List<Employee> employees;
    private List<User> users;

    private Employee employee = new Employee();
   // private User user = new User();

    private String name;
    private String username;
    private String password;
    private String salaryFrom;
    private String salaryTo;
    private VSATransport vsaTransport;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalaryFrom() {
        return salaryFrom;
    }

    public void setSalaryFrom(String salaryFrom) {
        this.salaryFrom = salaryFrom;
    }

    public String getSalaryTo() {
        return salaryTo;
    }

    public void setSalaryTo(String salaryTo) {
        this.salaryTo = salaryTo;
    }

    @PostConstruct
    @GetMapping(value = "/all")
    public void init() {
        employees = employeeService.findAll();
    }


    //API lay list Employee trên url.
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllAdmins() {
        List<Employee> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    public String add() {
        this.employee = new Employee();
        return "/employee-create.xhtml?faces-redirect=true";
    }

    public String save() {
        this.employeeService.create(employee);
        employees = employeeService.findAll();
        return "/employee-list.xhtml?faces-redirect=true";
    }

    //API them moi một Employee trên url.
    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Employee " + employee.getName());
        employeeService.create(employee);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/employees/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
   }


    public String delete(Employee employee) {
        this.employeeService.delete(employee);
        employees = employeeService.findAll();
        return "/employee-list.xhtml?faces-redirect=true";
    }


    //API xóa một Employee với ID trên url.
    @RequestMapping(value = "/employees/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Admin with id " + id);

        Employee employee = employeeService.findById(id);
        if (employee == null) {
            System.out.println("Unable to delete. Employee with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

        employeeService.delete(employee);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }


    public String viewEdit(Employee employee) {
        this.employee = employee;
        return "/employee-edit.xhtml?faces-redirect=true";
    }

    public String editEmployee(Employee employee) {
        this.employeeService.update(employee);
        employees = employeeService.findAll();
        return "/employee-list.xhtml?faces-redirect=true";
    }


    //API cập nhật một Employee với ID trên url.
    @RequestMapping(value = "/employees/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Employee> updateAdmin(@PathVariable("id") long id, @RequestBody Employee employee) {
        System.out.println("Updating Employee " + id);

        Employee currentEmployee = employeeService.findById(id);

        if (currentEmployee == null) {
            System.out.println("Employee with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

        currentEmployee.setName(employee.getName());
        currentEmployee.setEmployeeCode(employee.getEmployeeCode());
        currentEmployee.setDateStart(employee.getDateStart());
        currentEmployee.setDepartment(employee.getDepartment());
        currentEmployee.setManager(employee.getManager());
        currentEmployee.setSalary(employee.getSalary());
        currentEmployee.setWork(employee.getWork());
        currentEmployee.setId(employee.getId());

        employeeService.update(currentEmployee);
        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }

    public String searchName(String name, String employeeCode, String department, Double salaryFrom, Double salaryTo) {
        employees = employeeService.findByName(name, employeeCode, department, salaryFrom, salaryTo);
        return "employee-list.xhtml?faces-redirect=true";
    }


    //API Find by ID
    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getOneEmployee(@PathVariable("id") Long id) {

        Employee employee = employeeService.findById(id);
        if (employee == null) {
            System.out.println("Employee with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }


    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
