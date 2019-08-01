package vn.vissoft.employee.controller;


import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.vissoft.employee.config.JwtProvider;
import vn.vissoft.employee.message.request.LoginForm;
import vn.vissoft.employee.message.response.JwtResponse;
import vn.vissoft.employee.model.User;
import vn.vissoft.employee.repository.UserRepository;
import vn.vissoft.employee.service.Employee;
import vn.vissoft.employee.service.EmployeeService;
import vn.vissoft.employee.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@ViewScoped
//@Named
//@Join(path = "/login", to = ("/login.jsf"))
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class LoginController implements Serializable {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

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

    @PostMapping(value = "/login",produces = "application/json")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        System.out.println(jwt);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }



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

//    @PostConstruct
//    @GetMapping("/login")
//    public String formlogin() {
//        return "login.jsf";
//    }
//    @PostConstruct
//    @GetMapping(value = "/all")
//    public String formLogin() {
//        return "login.jsf";
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtProvider.generateJwtToken(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
//    }

//    public void login1() {
//        users = userService.findAll();
//        FacesContext context = FacesContext.getCurrentInstance();
//        users.forEach(
//                user -> {
//                    if (this.username.equals(user.getUsername()) && this.password.equals(user.getPassword())) {
//                        context.getExternalContext().getSessionMap().put("user", username);
//                        try {
//                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginController",user);
//                            context.getExternalContext().redirect(HOME_PAGE_REDIRECT);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        //Send an error message on Login Failure
//                        context.addMessage(null, new FacesMessage("Login Failed. Check username or password."));
//                    }
//                }
//        );
//    }
//
//    public void logout() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.getExternalContext().invalidateSession();
//        try {
//            context.getExternalContext().redirect("/login.jsf");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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
