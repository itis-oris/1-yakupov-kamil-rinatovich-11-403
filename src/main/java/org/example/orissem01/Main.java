package org.example.orissem01;

import org.example.orissem01.exceptions.DublicateLoginException;
import org.example.orissem01.models.User;
import org.example.orissem01.repositories.UserRepositoryImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User user = new User();
        user.setLogin("kamilyakupov2@mail.ru");
        user.setPassword("asdfasfasdf");
        user.setName("akfsdmasdf");
        user.setSurname("fasdfasdf");
        user.setRole("dsf");
        try {
            userRepository.addUser(user);
        } catch (SQLException e) {
            throw new DublicateLoginException("Пользователь с таким логином уже существует");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
