package org.example.orissem01.repositories;

import org.example.orissem01.models.Slot;
import org.example.orissem01.models.Transaction;
import org.example.orissem01.models.User;
import org.example.orissem01.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    public List<Transaction> getTransactions() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        List<Transaction> transactions = new ArrayList<>();
        String sql = """
             select t.transaction_id Ttransaction_id, t.from_account_id Tfrom_account_id,
                    t.to_account_id Tto_account_id, t.slot_id Tslot_id,
                    t.date Tdate, t.time Ttime, t.comment Tcomment,
             
                    a.account_id Aaccount_id, a.login Alogin, a.password Apassword,
                    a.name Aname, a.surname Asurname, a.role Arole,
             
                    b.account_id Baccount_id, b.login Blogin, b.password Bpassword,
                    b.name Bname, b.surname Bsurname, b.role Brole,
             
                    s.slot_id Sslot_id, s.name Sname, s.date Sdate, s.time Stime, s.type Stype
                    from transactions t
                    join accounts a on t.from_account_id = a.account_id
                    join accounts b on t.to_account_id = b.account_id
                    join slots s on t.slot_id = s.slot_id
                    """;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            transactions.add(mapTransaction(resultSet));
        }

        statement.close();
        resultSet.close();
        connection.close();

        return transactions;
    }

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

    private Transaction mapTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        User fromUser = new User();
        User toUser = new User();
        Slot slot = new Slot();

        fromUser.setId(resultSet.getLong("Aaccount_id"));
        fromUser.setLogin(resultSet.getString("Alogin"));
        fromUser.setPassword(resultSet.getString("Apassword"));
        fromUser.setName(resultSet.getString("Aname"));
        fromUser.setSurname(resultSet.getString("Asurname"));
        fromUser.setRole(resultSet.getString("Arole"));

        toUser.setId(resultSet.getLong("Baccount_id"));
        toUser.setLogin(resultSet.getString("Blogin"));
        toUser.setPassword(resultSet.getString("Bpassword"));
        toUser.setName(resultSet.getString("Bname"));
        toUser.setSurname(resultSet.getString("Bsurname"));
        toUser.setRole(resultSet.getString("Brole"));

        slot.setId(resultSet.getLong("Sslot_id"));
        slot.setName(resultSet.getString("Sname"));
        slot.setDate(resultSet.getString("Sdate"));
        slot.setTime(resultSet.getString("Stime"));
        slot.setType(resultSet.getString("Stype"));

        String date = resultSet.getString("Tdate");
        String time = resultSet.getString("Ttime");
        String comment = resultSet.getString("Tcomment");

        transaction.setFromUser(fromUser);
        transaction.setToUser(toUser);
        transaction.setSlot(slot);
        transaction.setDate(date);
        transaction.setTime(time.split("\\.")[0]);
        transaction.setComment(comment);

        return transaction;
    }
}
