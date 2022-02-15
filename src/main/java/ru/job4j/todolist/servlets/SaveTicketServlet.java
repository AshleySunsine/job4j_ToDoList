package ru.job4j.todolist.servlets;

import ru.job4j.todolist.model.Ticket;
import ru.job4j.todolist.model.User;
import ru.job4j.todolist.repository.ToDoStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class SaveTicketServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        ToDoStore toDoStore = new ToDoStore();
        String descripton = req.getParameter("description");
        String name = req.getParameter("todoName");
        String userEmail = req.getParameter("userEmail");

        String[] categoryId = req.getParameterValues("cIds");

        User user = toDoStore.findUserByEmail(userEmail);
        Timestamp created = new Timestamp(System.currentTimeMillis());
        boolean done = false;
        Ticket newTicket = new Ticket(name, descripton, created, done, user);

        toDoStore.addTicket(newTicket, categoryId);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
