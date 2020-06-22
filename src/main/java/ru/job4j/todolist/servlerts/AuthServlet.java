package ru.job4j.todolist.servlerts;

import ru.job4j.todolist.model.User;
import ru.job4j.todolist.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        List<User> userList = PsqlStore.instOf().findByName(login);
        if (!userList.isEmpty()) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", login);
            sc.setAttribute("userUUID", userList.get(0).getId());
            req.getRequestDispatcher("index.html").forward(req, resp);
        } else {
            resp.setStatus(401);
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.html").forward(req, resp);
        }
    }
}
