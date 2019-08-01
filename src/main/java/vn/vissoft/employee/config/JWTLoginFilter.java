//package vn.vissoft.employee.config;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.json.GsonJsonParser;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import vn.vissoft.employee.message.response.JwtResponse;
//import vn.vissoft.employee.model.User;
//import vn.vissoft.employee.service.TokenAuthenticationService;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collections;
//
///**
// * Created by nhs3108 on 29/03/2017.
// */
//public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
//
//    @Autowired
//    JwtProvider jwtProvider;
//
//    public JWTLoginFilter(String url, AuthenticationManager authManager) {
//        super(new AntPathRequestMatcher(url));
//        setAuthenticationManager(authManager);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        User credentials = new User(request.getParameter("username"), request.getParameter("password"));
//        return getAuthenticationManager().authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        credentials.getUsername(),
//                        credentials.getPassword(),
//                        Collections.emptyList()
//                )
//        );
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
////        TokenAuthenticationService.addAuthentication(response, authResult.getName());
//        String token = jwtProvider.generateJwtToken(authResult);
//        JwtResponse jwtResponse = new JwtResponse();
//        jwtResponse.setUsername(authResult.getName());
//        jwtResponse.setAccessToken(token);
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.getWriter().print(objectMapper.writeValueAsString(jwtResponse));
//    }
//}
//
