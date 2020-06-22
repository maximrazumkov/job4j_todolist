package ru.job4j.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.model.User;

import java.util.List;
import java.util.function.Function;

public class PsqlStore implements Store {

    private SessionFactory sf;

    private PsqlStore() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        this.sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public List<User> findByName(String name) {
        return this.tx(
                session -> {
                   Query<User> query = session.createQuery("from User u where u.name = :name");
                   query.setParameter("name", name);
                   return query.list();
                }
        );
    }

    @Override
    public Integer createUser(User user) {
        return this.tx(session -> {
            session.save(user);
            return user.getId();
        });
    }

    @Override
    public List<Item> findAllTodo() {
        return this.tx(
                session -> session.createQuery("from ru.job4j.todolist.model.Item").list()
        );
    }

    @Override
    public Integer createTodo(Item item) {
        return this.tx(
                session -> {
                    session.save(item);
                    return item.getId();
                }
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
