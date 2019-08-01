//package vn.vissoft.employee.fillter;
//
//import org.springframework.stereotype.Component;
//import vn.vissoft.employee.model.User;
//
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
////@WebFilter(urlPatterns =  "/*")
//public class LoginFillter implements Filter {
//
//    public LoginFillter()
//    {
//        System.out.println("");
//    }
//    /**
//     * Checks if user is logged in. If not it redirects to the login.xhtml page.
//     */
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // Get the loginBean from session attribute
//        System.out.println("filter is called...");
//        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
//        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        ((HttpServletResponse)response).setHeader("Access-Control-Max-Age", "3600");
//        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "Origin, Authorization, X-Requested-With, Content-Type, Accept, token1");
//        if(((HttpServletRequest)request).getRequestURI().contains("/login"))
//        {
//            chain.doFilter(request, response);
//            return;
//        }
//        User user = (User) ((HttpServletRequest)request).getSession().getAttribute("loginController");
//
//        // For the first application request there is no loginBean in the session so user needs to log in
//        // For other requests loginBean is present but we need to check if user has logged in successfully
//        if (user == null) {
//            String contextPath = ((HttpServletRequest)request).getContextPath();
//            ((HttpServletResponse)response).sendRedirect(contextPath + "/login.jsf");
//
//            return;
//        }
//
//        chain.doFilter(request, response);
//
//    }
//
//    public void init(FilterConfig config) throws ServletException {
//        // Nothing to do here!
//    }
//
//    public void destroy() {
//        // Nothing to do here!
//    }
//
//}
//
