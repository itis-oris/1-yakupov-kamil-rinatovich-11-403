package org.example.orissem01.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.orissem01.services.UserService;

import java.io.IOException;

@WebServlet("/user/records")
public class UserSlotsServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resource = userService.getUserRecords(request);
        request.getRequestDispatcher(resource).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resource = userService.updateUserRecord(request);
        userService.getUserRecords(request);
        request.getRequestDispatcher(resource).forward(request, response);
    }
}
