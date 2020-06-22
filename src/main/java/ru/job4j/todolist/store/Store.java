package ru.job4j.todolist.store;

import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.model.User;

import java.util.List;

public interface Store {
    List<Item> findAllTodo();
    Integer createTodo(Item item);
    Integer createUser(User user);
    List<User> findByName(String name);
}
