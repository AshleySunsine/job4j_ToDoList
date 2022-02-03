package ru.job4j.todolist;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.util.List;

public class ToDoStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Ticket addTicket(Ticket ticket) {
        Session session = sf.openSession();
        session.beginTransaction();
        ticket.setCreated(new Timestamp(System.currentTimeMillis()));
        Integer id = (Integer) session.save(ticket);
        ticket.setId(id);
        session.getTransaction().commit();
        session.close();
        return ticket;
    }

    @Override
    public boolean delete(int id) {
        boolean out = false;
        Session session = sf.openSession();
        session.beginTransaction();
        Ticket it = session.load(Ticket.class, id);
        session.delete(it);
        if ((session.load(Ticket.class, id)) == null) {
            out = true;
        }
        session.getTransaction().commit();
        session.close();
        return out;
    }

    @Override
    public boolean replace(int id, Ticket ticket) {
        Session session = sf.openSession();
        session.beginTransaction();
        ticket.setId(id);
        session.replicate(ticket, ReplicationMode.OVERWRITE);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<Ticket> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List items = session.createQuery("from ru.job4j.todolist.Ticket").list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public List<Ticket> findAllDone() {
        Session session = sf.openSession();
        session.beginTransaction();
        List items = session.createSQLQuery(
                        "select * from ticket where done=(" + true + ");")
                .addEntity(Ticket.class).list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public List<Ticket> findAllNotDone() {
        Session session = sf.openSession();
        session.beginTransaction();
        List items = session.createSQLQuery(
                        "select * from ticket where done=(" + false + ");")
                .addEntity(Ticket.class).list();
        session.getTransaction().commit();
        session.close();
        return items;
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
