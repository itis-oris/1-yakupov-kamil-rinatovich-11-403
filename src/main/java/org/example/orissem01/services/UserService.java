package org.example.orissem01.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.orissem01.exceptions.NoSuchUserException;
import org.example.orissem01.models.User;
import org.example.orissem01.repositories.UserRepositoryImpl;
import org.example.orissem01.utils.Password;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private final UserRepositoryImpl userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    public void getAll(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        List<User> users = userRepository.getUsers();
        request.setAttribute("users", users);
    }

    public User findUserByLogin(HttpServletRequest request) throws SQLException, ClassNotFoundException, NoSuchUserException {
        return userRepository.findUserByLogin(request.getParameter("login"))
                .orElseThrow(() -> new NoSuchUserException("Пользователя с таким логином не существует"));
    }

    public void addUser(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        String login    = request.getParameter("login");
        String password = request.getParameter("password");
        String name     = request.getParameter("name");
        String surname  = request.getParameter("surname");
        String role     = request.getParameter("role");

        User user = new User();
        user.setLogin(login);
        user.setName(name);
        user.setSurname(surname);
        user.setRole(role);

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        user.setPassword(bCrypt.encode(password));

        userRepository.addUser(user);
    }

    public void regUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        String login    = request.getParameter("login");
        String password = request.getParameter("password");
        String name     = request.getParameter("name");
        String surname  = request.getParameter("surname");
        String role     = request.getParameter("role");

        if (isEmpty(login) || isEmpty(password) || isEmpty(name)
                || isEmpty(surname) || isEmpty(role)){
            request.setAttribute("errormessage", "Введите корректные данные");
            request.getRequestDispatcher("/reg.ftl").forward(request, response);
        } else {
            try {
                addUser(request);
            } catch (SQLException e){
                request.setAttribute("errormessage", "Пользователь с таким логином уже существует");
                request.getRequestDispatcher("/reg.ftl").forward(request, response);
                return;
            } catch (ClassNotFoundException e){
                request.setAttribute("errormessage", "Что-то пошло не так, попробуйте еще раз...");
                request.getRequestDispatcher("/reg.ftl").forward(request, response);
                return;
            }

            session.setAttribute("user", login);
            request.getRequestDispatcher("/home.ftl").forward(request, response);
        }
    }

    public void checkUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String resource = "/home.ftl";

        if(session == null || session.getAttribute("user") == null) {
            String login    = request.getParameter("login");
            String password = request.getParameter("password");

            if (isEmpty(login) || isEmpty(password)){
                request.setAttribute("errormessage", "Логин и пароль не должны быть пустыми");
                resource = "/login.ftl";
            } else {
                try {
                    String userPassword = userRepository.getUserPasswordByLogin(login);
                    if (userPassword == null){
                        request.setAttribute("errormessage", "Пользователя с таким логином не существует");
                        resource = "/login.ftl";
                    } else {
                        if (Password.matches(password, userPassword)){
                            session = request.getSession(true);
                            session.setAttribute("user", login);
                        } else {
                            request.setAttribute("errormessage", "Неверный пароль, попробуйте еще раз!");
                            resource = "/login.ftl";
                        }
                    }
                } catch (Exception e) {
                    request.setAttribute("errormessage", "Что-то пошло не так, попробуйте еще раз...");
                    resource = "/login.ftl";
                }

            }

        }

        request.getRequestDispatcher(resource).forward(request, response);
    }

    private boolean isEmpty(String paramName) {
        return paramName == null || paramName.isEmpty();
    }
}
