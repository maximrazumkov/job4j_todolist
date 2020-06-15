package ru.job4j.todolist.store;

import ru.job4j.todolist.model.Item;

import java.util.List;

public interface Store {
    List<Item> findAllTodo();
    Integer createTodo(Item item);
}
