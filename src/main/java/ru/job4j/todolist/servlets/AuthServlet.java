package ru.job4j.todolist.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todolist.model.User;
import ru.job4j.todolist.Store;
import ru.job4j.todolist.repository.ToDoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        Store dbStore = new ToDoStore();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = dbStore.findUserByEmail(email);
        if ((user != null) && (user.getPassword().equals(password))) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}