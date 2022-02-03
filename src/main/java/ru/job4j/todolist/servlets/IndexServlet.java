package ru.job4j.todolist.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todolist.Ticket;
import ru.job4j.todolist.ToDoStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class IndexServlet extends HttpServlet {
   private class ListsTicket {
        List<Ticket> ticketDone;
        List<Ticket> ticketNotDone;

        public ListsTicket(List<Ticket> ticketDone, List<Ticket> ticketNotDone) {
            this.ticketDone = ticketDone;
            this.ticketNotDone = ticketNotDone;
        }
    }

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        ToDoStore toDoStore = new ToDoStore();
        List<Ticket> ticketDone =  toDoStore.findAllDone();
        List<Ticket> ticketNotDone =  toDoStore.findAllNotDone();
        ListsTicket listsTicket = new ListsTicket(ticketDone, ticketNotDone);
        String json = GSON.toJson(listsTicket);
        System.out.println("ServletWorks!!!");
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }
}
