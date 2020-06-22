package ru.job4j.todolist.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usr")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof User) {
            User user = (User) o;
            return user.id.equals(this.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '\"' +
                '}';
    }
}
