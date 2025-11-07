package org.example.orissem01.repositories.interfaces;

import org.example.orissem01.models.Record;
import org.example.orissem01.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    List<User> getAll() throws SQLException, ClassNotFoundException;

    Optional<User> findUserByLogin(String login) throws SQLException, ClassNotFoundException;

    void addUser(User user) throws SQLException, ClassNotFoundException;

    String getUserPasswordByLogin(String login) throws SQLException, ClassNotFoundException;

    void updateUser(User user) throws SQLException, ClassNotFoundException;

    void deleteUserByLogin(String login) throws SQLException, ClassNotFoundException;

    Record mapRecord(ResultSet resultSet) throws SQLException;

    User mapUser(ResultSet resultSet) throws SQLException, ClassNotFoundException;

    List<Record> getRecordsByUserId(Long userId) throws SQLException, ClassNotFoundException;


}
