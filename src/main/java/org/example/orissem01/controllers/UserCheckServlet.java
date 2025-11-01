package org.example.orissem01.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.orissem01.services.UserService;

import java.io.IOException;

public class UserCheckServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String resource = "/app.ftl";

        if(session == null || session.getAttribute("user") == null) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (userService.exist(username, password)){
                session = request.getSession(true);
                session.setAttribute("user", username);
                resource = "/app.ftl";
            } else {
                request.setAttribute("errormessage", "Неверное имя пользователя или пароль");
                resource = "/login.ftl";
            }
        }

        request.getRequestDispatcher(resource).forward(request, response);
    }

}

