package org.example.orissem01.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.orissem01.exceptions.ConnectionException;
import org.example.orissem01.exceptions.MySQLException;
import org.example.orissem01.exceptions.NotValidPassword;
import org.example.orissem01.services.UserService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/user/update")
public class UserUpdateServlet extends HomeServlet{

    private UserService userService;

    @Override
    public void init(){
        ServletContext servletContext = getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("user", userService.findUserByLogin(request));
        request.setAttribute("context", request.getContextPath());
        request.setAttribute("errormessage", request.getParameter("errormessage"));
        request.getRequestDispatcher("/userUpdate.ftl").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resource = "/user/update";
        try {
            userService.updateUser(request);
        } catch (NotValidPassword e){
            resource = resource + "?errormessage=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        } catch (MySQLException | ConnectionException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(String.format("%s%s", request.getContextPath(), resource));
    }
}
