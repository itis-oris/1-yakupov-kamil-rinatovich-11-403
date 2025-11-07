package org.example.orissem01.repositories.interfaces;

import org.example.orissem01.models.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ITransactionRepository {

    List<Transaction> getTransactions() throws SQLException, ClassNotFoundException;

    void addTransaction(Transaction transaction) throws SQLException, ClassNotFoundException;

    Transaction mapTransaction(ResultSet resultSet) throws SQLException;
}
