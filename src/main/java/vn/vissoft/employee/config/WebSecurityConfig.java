package vn.vissoft.employee.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.vissoft.employee.service.UserService;

import javax.sql.DataSource;

/**
 * Created by nhs3108 on 29/03/2017.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserService userService;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/auth/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
//                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/auth/**").permitAll()
                .anyRequest().authenticated();
//                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//            auth.inMemoryAuthentication().withUser("admin").password("{noop}password").roles("ADMIN");
//            auth.inMemoryAuthentication().withUser("admin1").password("{noop}password").roles("ADMIN");
//
//
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enable from usersss where username=?")
//                .authoritiesByUsernameQuery("select username, authority from author where username=?");

        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
        provider.setUserService(userService);
        auth.authenticationProvider(provider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

   // @Bean
    public JWTAuthenticationFilter authenticationJwtTokenFilter() throws Exception {
        return new JWTAuthenticationFilter();
    }

//    @Bean
//    public JWTLoginFilter loginFilter() throws Exception {
//        return new JWTLoginFilter("/login", authenticationManager());
//    }
//    @Bean
//    public JwtProvider jwtProviderBean() throws Exception {
//        return new JwtProvider();
//    }

}
