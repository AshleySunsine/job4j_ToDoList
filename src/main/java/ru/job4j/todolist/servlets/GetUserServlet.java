package ru.job4j.todolist.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todolist.model.User;
import ru.job4j.todolist.repository.ToDoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GetUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    /*    resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        User user = dbStore.findUserByEmail(email);
        final Gson gson = new GsonBuilder().create();
        String userJson = gson.toJson(user);*/
    }
}
