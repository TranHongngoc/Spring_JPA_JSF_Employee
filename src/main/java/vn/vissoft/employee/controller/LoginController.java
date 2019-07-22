package vn.vissoft.employee.controller;


import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.GetMapping;
import vn.vissoft.employee.model.User;
import vn.vissoft.employee.service.Employee;
import vn.vissoft.employee.service.EmployeeService;
import vn.vissoft.employee.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ViewScoped
@Named
@Join(path = "/login", to = ("/login.jsf"))
public class LoginController implements Serializable {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserService userService;

    private List<Employee> employees;
    private List<User> users;

    private Employee employee = new Employee();

    private static final long serialVersionUID = -9107952969237527819L;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LoginController.class);

    public static final String HOME_PAGE_REDIRECT =
            "/employee-list.jsf?faces-redirect=true";
    public static final String LOGOUT_PAGE_REDIRECT =
            "/login.jsf?faces-redirect=true";

    private String username;
    private String password;
    private User currentUser;

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    @PostConstruct
    @GetMapping("/login")
    public String formlogin() {
        return "login.jsf";
    }
//    @PostConstruct
//    @GetMapping(value = "/all")
//    public String formLogin() {
//        return "login.jsf";
//    }

    public void login1() {
        users = userService.findAll();
        FacesContext context = FacesContext.getCurrentInstance();
        users.forEach(
                user -> {
                    if (this.username.equals(user.getUsername()) && this.password.equals(user.getPassword())) {
                        context.getExternalContext().getSessionMap().put("user", username);
                        try {
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginController",user);
                            context.getExternalContext().redirect(HOME_PAGE_REDIRECT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Send an error message on Login Failure
                        context.addMessage(null, new FacesMessage("Login Failed. Check username or password."));
                    }
                }
        );
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        try {
            context.getExternalContext().redirect("/login.jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoggedIn() {
        return users != null;
    }

    public String isLoggedInForwardHome() {
        if (isLoggedIn()) {
            return HOME_PAGE_REDIRECT;
        }

        return null;
    }



}
