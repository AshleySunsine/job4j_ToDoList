package ru.job4j.todolist.repository;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todolist.Store;
import ru.job4j.todolist.model.Category;
import ru.job4j.todolist.model.Ticket;
import ru.job4j.todolist.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ToDoStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void addTicket(Ticket ticket, String[] categoryIds) {
         this.tx(session -> {
             Map<Integer, Category> mapCat = (Map<Integer, Category>) session.createQuery("from ru.job4j.todolist.model.Category")
                     .list()
                     .stream()
                     .collect(Collectors
                             .toMap(Category::getId, Function.identity()));
             for (String c : categoryIds) {
                 ticket.getCategories().add(mapCat.get(Integer.parseInt(c)));
             }
            ticket.setCreated(new Timestamp(System.currentTimeMillis()));
            Integer id = (Integer) session.save(ticket);
            ticket.setId(id);
            return ticket;
        });
    }

    @Override
    public boolean delete(int id) {
        return this.tx(session -> {
            boolean out = false;
            Ticket it = session.load(Ticket.class, id);
            session.delete(it);
            if ((session.load(Ticket.class, id)) == null) {
                out = true;
            }
            return out;
        });
    }

    @Override
    public boolean replace(int id, Ticket ticket) {
        return this.tx(session -> {
            ticket.setId(id);
            session.replicate(ticket, ReplicationMode.OVERWRITE);
            return true;
        });
    }

    @Override
    public List<Ticket> findAll() {
        return this.tx(session -> {
            List items = session.createQuery("select distinct c from ru.job4j.todolist.model.Ticket c join fetch c.categories")
                    .list();
            return items;
        });
    }

    @Override
    public List<Ticket> findAllDone() {
        return this.tx(session -> {
            List items = session.createQuery(
                            "select distinct c from ru.job4j.todolist.model.Ticket c join fetch c.categories where c.done = (:isDone)")
                    .setParameter("isDone", true).list();
            return items;
        });
    }

    @Override
    public List<Ticket> findAllNotDone() {
        return this.tx(session -> {
            List items = session.createQuery(
                            "select distinct c from ru.job4j.todolist.model.Ticket c join fetch c.categories where c.done = (:isDone)")
                    .setParameter("isDone", false).list();
            return items;
        });
    }

    @Override
    public boolean doneNotDone(int id) {
        return this.tx(session -> {
                Ticket ticket = session.load(Ticket.class, id);
                ticket.setDone(!ticket.isDone());
                return true;
        });
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        session.beginTransaction();
        try {
            T rsl = command.apply(session);
            session.getTransaction().commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean addUser(User user) {
        return this.tx(session -> {
            Integer id = (Integer) session.save(user);
            user.setId(id);
            return true;
        });
    }

    @Override
    public boolean deleteUser(int id) {
        return this.tx(session -> {
            boolean out = false;
            User us = session.load(User.class, id);
            session.delete(us);
            if ((session.load(User.class, id)) == null) {
                out = true;
            }
            return out;
        });
    }

    @Override
    public List<User> findAllUser() {
        return this.tx(session -> {
            List items = session.createQuery("from ru.job4j.todolist.model.User")
                    .list();
            return items;
        });
    }

    @Override
    public User findUserByEmail(String email) {
        return this.tx(session -> {
            User user = (User) session.createSQLQuery("select * from usertick where email = ( :email );")
                      .setParameter("email", email).addEntity(User.class).uniqueResult();
            System.out.println(user);
            return user;
                }
        );
    }

    @Override
    public User findUserById(int id) {
        return this.tx(session ->
                session.load(User.class, id)
        );
    }

    @Override
    public List<Category> findAllCategory() {
        return this.tx(session -> {
            List categories = session.createQuery("from ru.job4j.todolist.model.Category")
                    .list();
            return categories;
        });
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static void main(String[] args) {
        /*ToDoStore store = new ToDoStore();
        Ticket tik = new Ticket("name", "description",
                new Timestamp(System.currentTimeMillis()), false, new User(0, "fromMain", "fromMain", "fromMain"));
        store.addTicket(tik, );
        System.out.println(store.findAll());*/
    }
}
