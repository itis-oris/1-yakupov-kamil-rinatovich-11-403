package org.example.orissem01.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.orissem01.services.UserService;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private final UserService userService = new UserService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/home.ftl").forward(request, response);
    }
}
