package ru.job4j.todolist.servlerts;

import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.model.User;
import ru.job4j.todolist.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TodoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Item> result = PsqlStore.instOf().findAllTodo();
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                resp.getOutputStream(), StandardCharsets.UTF_8), true);
        writer.println(result);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Integer id = (Integer) req.getSession().getAttribute("userUUID");
        PsqlStore.instOf().createTodo(new Item(name, true, false, new User(id)));
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
    }
}
