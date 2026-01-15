package org.example.orissem01.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.orissem01.exceptions.ConnectionException;
import org.example.orissem01.exceptions.DublicateUserException;
import org.example.orissem01.services.UserService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(){
        ServletContext servletContext = getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        request.setAttribute("context", request.getContextPath());
        request.setAttribute("errormessage", request.getParameter("errormessage"));
        request.getRequestDispatcher("/reg.ftl").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resource = "/reg";
        try {
            if (userService.regUser(request)) {
                HttpSession session = request.getSession(true);
                String login = request.getParameter("login");
                session.setAttribute("userLogin", login);
                resource = "/home";
            }
        } catch (ConnectionException e) {
            throw new RuntimeException(e.getMessage());
        } catch (DublicateUserException e) {
            resource = resource + "?errormessage=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }
        response.sendRedirect(String.format("%s%s", request.getContextPath(), resource));
    }
}
