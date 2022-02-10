package ru.job4j.todolist.filter;

import ru.job4j.todolist.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        resp.setContentType("text/plain; charset=utf-8");
        String uri = req.getRequestURI();
        System.out.println(uri + " AuthServletWorks");
        if (uri.endsWith("auth.do")) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (uri.endsWith("registry.do")) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.addHeader("REQUIRES_AUTH", "1");
            resp.sendRedirect(req.getContextPath());
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {
    }
}