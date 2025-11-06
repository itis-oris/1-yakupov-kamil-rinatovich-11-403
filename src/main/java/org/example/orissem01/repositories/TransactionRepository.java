package org.example.orissem01.repositories;

import org.example.orissem01.models.Transaction;
import org.example.orissem01.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRepository {

    public void addTransaction(Transaction transaction) throws SQLException, ClassNotFoundException{
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);

        //Вытаскиваем айди
        String sqlSelectId = "select id from nextval('transaction_id_seq') as id";
        PreparedStatement statement = connection.prepareStatement(sqlSelectId);
        ResultSet resultSet = statement.executeQuery();


        if(resultSet.next()){
            Long id = resultSet.getLong("id");
            transaction.setId(id);
        }

        resultSet.close();
        statement.close();

        Long fromUserid = transaction.getFromUser().getId();
        Long toUserid = transaction.getToUser().getId();
        Long slotId = transaction.getSlot().getId();

        //Меняем владельца
        String sqlUpdate = """
                update account_slot
                set account_id = ?
                where account_id = ? AND slot_id = ?;
                """;
        statement = connection.prepareStatement(sqlUpdate);
        statement.setLong(1, toUserid);
        statement.setLong(2, fromUserid);
        statement.setLong(3, slotId);

        statement.executeUpdate();
        statement.close();

        //Меняем статус
        String sqlUpdateStaus = """
                update records
                set status = 'Запланирована'
                where account_slot_id =
                      (SELECT account_slot_id
                       from account_slot
                       where account_id = ? AND slot_id = ?)
                """;
        statement = connection.prepareStatement(sqlUpdateStaus);
        statement.setLong(1, toUserid);
        statement.setLong(2, slotId);

        statement.executeUpdate();
        statement.close();

        //Вставляем транзакцию
        String sqlInsert = """
            insert into transactions(transaction_id, from_account_id, to_account_id, slot_id, comment)
            VALUES (?, ?, ?, ?, ?);
            """;

        statement = connection.prepareStatement(sqlInsert);
        statement.setLong  (1, transaction.getId());
        statement.setLong  (2, fromUserid);
        statement.setLong  (3, toUserid);
        statement.setLong  (4, slotId);
        statement.setString(5, transaction.getComment());

        statement.executeUpdate();
        statement.close();

        //Вытаскиваем дату и время
        String sqlSelect = "select date, time from transactions where transaction_id = ?";
        statement = connection.prepareStatement(sqlSelect);
        statement.setLong(1, transaction.getId());
        resultSet = statement.executeQuery();

        if (resultSet.next()){
            transaction.setDate(resultSet.getString("date"));
            transaction.setTime(resultSet.getString("time"));
        }

        resultSet.close();
        statement.close();

        connection.commit();
        connection.close();
    }
}
