package ru.job4j.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todolist.model.Item;

import java.util.List;

public class PsqlStore implements Store {

    private final SessionFactory sf;

    private PsqlStore() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        this.sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public List<Item> findAllTodo() {
        List<Item> result = new ArrayList<>();
        try {
            Session session = this.sf.openSession();
            session.beginTransaction();
            result = session.createQuery("from ru.job4j.todolist.model.Item").list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer createTodo(Item item) {
        Integer result = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            result = item.getId();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
