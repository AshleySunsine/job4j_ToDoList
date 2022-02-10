package ru.job4j.todolist;


import ru.job4j.todolist.model.Ticket;
import ru.job4j.todolist.model.User;

import java.util.List;

public interface Store extends AutoCloseable {
   Ticket addTicket(Ticket ticket);
   boolean delete(int id);
   List<Ticket> findAll();
   List<Ticket> findAllDone();
   List<Ticket> findAllNotDone();
   boolean replace(int id, Ticket ticket);
   boolean doneNotDone(int id);

   boolean addUser(User user);
   boolean deleteUser(int id);
   List<User> findAllUser();
   User findUserByEmail(String email);
   User findUserById(int id);
}
