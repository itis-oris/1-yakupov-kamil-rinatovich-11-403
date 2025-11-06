package org.example.orissem01.repositories;

import org.example.orissem01.models.Record;
import org.example.orissem01.models.Slot;
import org.example.orissem01.models.User;
import org.example.orissem01.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecordRepository {

    public Optional<Record> findRecordById(Long id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        String sql = """
            select r.account_slot_id, chats_count, status, comment,
                               a.account_id, login, password, a.name as a_name, surname, role,
                               s.slot_id, s.name as s_name, date, time, type
                        from records r
                        join public.account_slot acs on r.account_slot_id = acs.account_slot_id
                        join public.accounts a on a.account_id = acs.account_id
                        join public.slots s on acs.slot_id = s.slot_id
            where r.account_slot_id = ?
            """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
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

    public List<Record> getRecords() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        List<Record> records = new ArrayList<>();
        String sql = """
                        select r.account_slot_id, chats_count, status, comment,
                               a.account_id, login, password, a.name as a_name, surname, role,
                               s.slot_id, s.name as s_name, date, time, type
                        from records r
                        join public.account_slot acs on r.account_slot_id = acs.account_slot_id
                        join public.accounts a on a.account_id = acs.account_id
                        join public.slots s on acs.slot_id = s.slot_id
                        """;
        PreparedStatement statement = connection.prepareStatement(sql);
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

    private Record mapRecord(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        Record record = new Record();
        User user = mapUser(resultSet);
        Slot slot = mapSlot(resultSet);

        record.setId(resultSet.getLong ("account_slot_id"));

        record.setUser(user);
        record.setSlot(slot);

        record.setChatsCount(resultSet.getInt("chats_count"));
        record.setStatus(resultSet.getString("status"));
        record.setComment(resultSet.getString("comment"));
        return record;
    }

    private User mapUser(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        User user = new User();
        Long id = resultSet.getLong  ("account_id");
        user.setId      (id);
        user.setLogin   (resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setName    (resultSet.getString("a_name"));
        user.setSurname (resultSet.getString("surname"));
        user.setRole    (resultSet.getString("role"));
        return user;
    }

    private Slot mapSlot(ResultSet resultSet) throws SQLException {
        Slot slot = new Slot();
        slot.setId      (resultSet.getLong  ("slot_id"));
        slot.setName    (resultSet.getString("s_name"));
        slot.setDate    (resultSet.getString("date"));
        slot.setTime    (resultSet.getString("time"));
        slot.setType    (resultSet.getString("type"));
        return slot;
    }

}
