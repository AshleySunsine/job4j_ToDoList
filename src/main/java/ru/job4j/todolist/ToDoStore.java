package ru.job4j.todolist;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

public class ToDoStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Ticket addTicket(Ticket ticket) {
        return this.tx(session -> {
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
            List items = session.createQuery("from ru.job4j.todolist.Ticket").list();
            return items;
        });
    }

    @Override
    public List<Ticket> findAllDone() {
        return this.tx(session -> {
            List items = session.createSQLQuery(
                            "select * from ticket where done=(" + true + ");")
                    .addEntity(Ticket.class).list();
            return items;
        });
    }

    @Override
    public List<Ticket> findAllNotDone() {
        return this.tx(session -> {
            List items = session.createSQLQuery(
                            "select * from ticket where done=(" + false + ");")
                    .addEntity(Ticket.class).list();
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
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public static void main(String[] args) {
        ToDoStore store = new ToDoStore();
        Ticket tik = new Ticket("name", "description",
                new Timestamp(System.currentTimeMillis()), false);
        store.addTicket(tik);
        System.out.println(store.findAll());
    }
}
