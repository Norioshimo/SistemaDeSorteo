package controller.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.login.LoginBean;


public class LoginFilter implements Filter {
    public static final String LOGIN_PAGE = "/login.xhtml";

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        LoginBean userManager = (LoginBean) httpServletRequest
                .getSession().getAttribute("loginBean");

        if (userManager != null) {
            if (userManager.estaLogeado()) {
                System.out.println("El usuario está logueado!!");
                // user is logged in, continue request
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                System.out.println("El usuario NO está logueado!!");
                // user is not logged in, redirect to login page
                try{
                httpServletResponse.sendRedirect(httpServletRequest
                        .getContextPath() + LOGIN_PAGE);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("La sesión NO está creada!!");
            // user is not logged in, redirect to login page
            httpServletResponse.sendRedirect(httpServletRequest
                    .getContextPath() + LOGIN_PAGE);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        System.out.println("LoginFilter Inicializada!!");
    }

    @Override
    public void destroy() {
        System.out.println("LoginFilter Destruida!!");
    }
}
