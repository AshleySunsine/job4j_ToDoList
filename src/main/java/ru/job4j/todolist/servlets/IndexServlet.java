package ru.job4j.todolist.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todolist.model.Ticket;
import ru.job4j.todolist.repository.ToDoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        ToDoStore toDoStore = new ToDoStore();

        Map<String, List<Ticket>> mapTiket = new HashMap<>();
        mapTiket.put("ticketDone", toDoStore.findAllDone());
        mapTiket.put("ticketNotDone", toDoStore.findAllNotDone());

        String json = GSON.toJson(mapTiket);
        System.out.println("ServletWorks!!!");
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
