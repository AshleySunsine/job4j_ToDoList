package ru.job4j.todolist.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private Timestamp created;
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "userName")
    private User userTick;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> categories = new HashSet<>();

    public Ticket() {
    }

    public Ticket(String name, String description, Timestamp created, boolean done, User user) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.done = done;
        this.userTick = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public User getUserTick() {
        return userTick;
    }

    public void setUserTick(User userTick) {
        this.userTick = userTick;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id && done == ticket.done && Objects.equals(name, ticket.name) && Objects.equals(description, ticket.description) && Objects.equals(created, ticket.created) && Objects.equals(userTick, ticket.userTick) && Objects.equals(categories, ticket.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created, done, userTick, categories);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}
