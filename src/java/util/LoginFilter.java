/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String url = req.getRequestURI();

        User user = (User) req.getSession().getAttribute("current_user");
        if (user == null) {
            if (url.contains("auth") || url.contains("logout")) {
                res.sendRedirect(req.getContextPath()+ "/faces/login.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (url.contains("register") || url.contains("login")) {
                res.sendRedirect(req.getContextPath()+ "/auth/projects?faces-redirect=true");
            } else if (url.contains("logout")) {
                req.getSession().invalidate();
                res.sendRedirect(req.getContextPath()+ "/");
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}
