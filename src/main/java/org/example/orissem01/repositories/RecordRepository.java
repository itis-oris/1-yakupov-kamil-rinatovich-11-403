package org.example.orissem01.repositories;

import org.example.orissem01.models.Record;
import org.example.orissem01.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecordRepository {

    public List<Record> getSheduledAndExchangedRecordsByUserId(Long userId) throws SQLException, ClassNotFoundException {
        String sql = """
            select r.account_slot_id, r.chats_count, r.status, r.comment,
                   s.name, s.date, s.time, s.type
            from slots s
            join account_slot acs on s.slot_id = acs.slot_id
            join records r on acs.account_slot_id = r.account_slot_id
            where account_id = ? AND (lower(r.status) = 'запланирована' OR lower(r.status) = 'отдается')
            AND s.date >= current_date AND s.time >= current_time
            order by date, time;
            """;
        return getRecordsByUserId(userId, sql);
    }
    public List<Record> getSheduledRecordsByUserId(Long userId) throws SQLException, ClassNotFoundException {
        String sql = """
            select r.account_slot_id, r.chats_count, r.status, r.comment,
                   s.name, s.date, s.time, s.type
            from slots s
            join account_slot acs on s.slot_id = acs.slot_id
            join records r on acs.account_slot_id = r.account_slot_id
            where account_id = ? AND (lower(r.status) = 'запланирована')
            AND s.date >= current_date AND s.time >= current_time
            order by date, time;
            """;
        return getRecordsByUserId(userId, sql);
    }

    public List<Record> getCompletedRecordsByUserId(Long userId) throws SQLException, ClassNotFoundException {
        String sql = """
            select r.account_slot_id, r.chats_count, r.status, r.comment,
                   s.name, s.date, s.time, s.type
            from slots s
            join account_slot acs on s.slot_id = acs.slot_id
            join records r on acs.account_slot_id = r.account_slot_id
            where account_id = ? AND lower(r.status) != 'запланирована' AND lower(r.status) != 'отдается'
            order by date, time;
            """;
        return getRecordsByUserId(userId, sql);
    }

    private List<Record> getRecordsByUserId(Long userId, String sql) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        List<Record> records = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, userId);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            records.add(mapRecord(resultSet));
        }

        statement.close();
        resultSet.close();
        connection.close();

        return records;
    }

    public void updateRecord(Record record) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);

        String sql = """
            update records
            set status = ?, comment = ?
            where account_slot_id = ?;
            """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, record.getStatus());
        statement.setString(2, record.getComment());
        statement.setLong  (3, record.getId());
        statement.executeUpdate();

        statement.close();
        connection.commit();
        connection.close();
    }

    public Optional<Record> findRecordByUserIdAndDate(Long userId, String date, String time) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        String sql = """
            select r.account_slot_id, r.chats_count, r.status, r.comment,
                   s.name, s.date, s.time, s.type
            from slots s
                     join account_slot acs on s.slot_id = acs.slot_id
                     join records r on acs.account_slot_id = r.account_slot_id
            where account_id = ? and date = ? and time = ?
            """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, userId);
        statement.setString(2, date);
        statement.setString(3, time);
        ResultSet resultSet = statement.executeQuery();

        Record record = null;
        if(resultSet.next()){
            record = mapRecord(resultSet);
        }

        statement.close();
        resultSet.close();
        connection.close();

        return Optional.ofNullable(record);
    }

    private Record mapRecord(ResultSet resultSet) throws SQLException {
        Record record = new Record();
        record.setId        (resultSet.getLong  ("account_slot_id"));
        record.setChatsCount(resultSet.getInt   ("chats_count"));
        record.setStatus    (resultSet.getString("status"));
        record.setComment   (resultSet.getString("comment"));
        record.setName      (resultSet.getString("name"));
        record.setDate      (resultSet.getString("date"));
        record.setTime      (resultSet.getString("time"));
        record.setType      (resultSet.getString("type"));
        return record;
    }
}
