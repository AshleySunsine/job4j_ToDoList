package ru.job4j.todolist.servlets;

import ru.job4j.todolist.model.Category;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        Map<Integer, Category> mapCat = toDoStore.findAllCategory()
                .stream()
                .collect(Collectors
                        .toMap(Category::getId, Function.identity()));
        for (String c : categoryId) {
            newTicket.getCategories().add(mapCat.get(Integer.parseInt(c)));
        }
        toDoStore.addTicket(newTicket);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
