package ru.job4j.todolist.servlets;

import jdk.vm.ci.meta.Local;
import ru.job4j.todolist.Ticket;
import ru.job4j.todolist.ToDoStore;

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
        String descripton = req.getParameter("description");
        String name = req.getParameter("todoName");
        Timestamp created = new Timestamp(System.currentTimeMillis());
        boolean done = false;
        Ticket newTicket = new Ticket(name, descripton, created, done);
        ToDoStore toDoStore = new ToDoStore();
        Ticket ticket = toDoStore.addTicket(newTicket);
        System.out.println(ticket);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
