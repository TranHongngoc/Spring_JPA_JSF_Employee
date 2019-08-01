package vn.vissoft.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import vn.vissoft.employee.repository.EmployeeRepository;
import vn.vissoft.employee.repository.EmployeeRepositoryImpl;
import vn.vissoft.employee.repository.UserRepository;
import vn.vissoft.employee.repository.UserRepositoryImpl;
import vn.vissoft.employee.service.EmployeeService;
import vn.vissoft.employee.service.EmployeeServiceImpl;
import vn.vissoft.employee.service.UserService;
import vn.vissoft.employee.service.UserServiceImpl;

import javax.faces.webapp.FacesServlet;

@EnableAutoConfiguration
@ComponentScan({"vn.vissoft.employee"})
public class EmployeeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Bean
    public EmployeeRepository employeeRepository(){
        return new EmployeeRepositoryImpl();
    }

    @Bean
    public EmployeeService employeeService(){
        return new EmployeeServiceImpl(employeeRepository());
    }

    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl();
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl(userRepository());
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        return new ServletRegistrationBean(servlet, "*.jsf");
    }




}
