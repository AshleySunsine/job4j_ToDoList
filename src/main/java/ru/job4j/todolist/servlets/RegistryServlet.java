package ru.job4j.todolist.servlets;

import ru.job4j.todolist.model.User;
import ru.job4j.todolist.Store;
import ru.job4j.todolist.repository.ToDoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Store dbStore = new ToDoStore();
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = dbStore.findUserByEmail(req.getParameter("email"));
        if (user == null) {
            dbStore.addUser(
                    new User(0, name, email, password));
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Такой email уже существует");
            req.getRequestDispatcher("registry.jsp").forward(req, resp);
        }
    }
}