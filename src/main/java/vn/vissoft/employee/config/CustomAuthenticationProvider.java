package vn.vissoft.employee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import vn.vissoft.employee.model.User;
import vn.vissoft.employee.model.UserPrinciple;
import vn.vissoft.employee.service.UserService;

import java.util.ArrayList;
import java.util.Objects;

public class CustomAuthenticationProvider implements AuthenticationProvider {


    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.findByName(authentication.getName());
        if(user!=null&& Objects.equals(user.getPassword(),authentication.getCredentials()))
        {
            UserDetails userDetails = UserPrinciple.build(user);
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            return result;
        }
        throw new UsernameNotFoundException("Khong tim thay thong tin user");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
