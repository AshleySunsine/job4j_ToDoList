package ru.job4j.todolist.servlets;

import ru.job4j.todolist.repository.ToDoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTicketServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("text"));
        System.out.println(req.getParameter("text"));
        ToDoStore toDoStore = new ToDoStore();
        toDoStore.delete(id);
    }
}
