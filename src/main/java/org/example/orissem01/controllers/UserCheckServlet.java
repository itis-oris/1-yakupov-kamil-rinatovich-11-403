package org.example.orissem01.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.orissem01.exceptions.ConnectionException;
import org.example.orissem01.exceptions.MySQLException;
import org.example.orissem01.services.UserService;
import org.example.orissem01.utils.Password;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/usercheck")
public class UserCheckServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(){
        ServletContext servletContext = getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resource = checkUser(request);
        response.sendRedirect(String.format("%s%s", request.getContextPath(), resource));
    }

    private String checkUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String resource = "/login";

        if (session == null || session.getAttribute("userLogin") == null) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            try {
                String userPassword = userService.getUserHashPassword(login);
                if (userPassword == null) {
                    resource = resource + "?errormessage=" + URLEncoder.encode("Пользователя с таким логином не существует", StandardCharsets.UTF_8);
                } else {
                    if (Password.matches(password, userPassword)) {
                        session = request.getSession(true);
                        session.setAttribute("userLogin", login);
                        resource = "/home";
                    } else {
                        resource = resource + "?errormessage=" + URLEncoder.encode("Неверный пароль, попробуйте еще раз!", StandardCharsets.UTF_8);
                    }
                }
            } catch (MySQLException | ConnectionException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return resource;
    }

}

