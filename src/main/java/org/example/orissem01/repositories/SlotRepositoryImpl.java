package org.example.orissem01.repositories;

import org.example.orissem01.models.Slot;
import org.example.orissem01.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SlotRepositoryImpl {

    public void addSlot(Slot slot) throws SQLException, ClassNotFoundException{
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);

        //Вытаскиваем айди
        String sqlSelectId = "select id from nextval('slot_id_seq') as id";
        PreparedStatement statement = connection.prepareStatement(sqlSelectId);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            Long id = resultSet.getLong("id");
            slot.setId(id);
        }

        resultSet.close();
        statement.close();

        //Вставляем слот
        String sqlInsert = """
            insert into slots(slot_id, name, date, time, type) values (?, ?, ?, ?, ?, ?);
            """;

        statement = connection.prepareStatement(sqlInsert);
        statement.setLong   (1, slot.getId());
        statement.setString (2, slot.getName());
        statement.setString (3, slot.getDate());
        statement.setString (4, slot.getTime());
        statement.setString (5, slot.getType());
        statement.setBoolean(6, slot.isExchange());
        statement.executeUpdate();

        statement.close();
        connection.commit();
        connection.close();
    }

    public Optional<Slot> findSlotByDate(String date, String time) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        String sql = """
            select slot_id, name, date, time, type
            from slots
            where date = ? AND time = ?
            """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, date);
        statement.setString(1, time);
        ResultSet resultSet = statement.executeQuery();

        Slot slot = null;
        if(resultSet.next()){
            slot = mapSlot(resultSet);
        }

        statement.close();
        resultSet.close();
        connection.close();

        return Optional.ofNullable(slot);
    }

    public List<Slot> getSlotsByUserId(Long userId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        List<Slot> slots = new ArrayList<>();
        String sql = """
            select s.slot_id, name, date, time, type
            from slots s
            join account_slot as acs on s.slot_id = acs.slot_id
            where account_id = ? AND date >= current_date AND time >= current_time
            order by date, time
            """;
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, userId);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            slots.add(mapSlot(resultSet));
        }

        statement.close();
        resultSet.close();
        connection.close();

        return slots;
    }

    private Slot mapSlot(ResultSet resultSet) throws SQLException {
        Slot slot = new Slot();
        slot.setId      (resultSet.getLong  ("slot_id"));
        slot.setName    (resultSet.getString("name"));
        slot.setDate    (resultSet.getString("date"));
        slot.setTime    (resultSet.getString("time"));
        slot.setType    (resultSet.getString("type"));
        slot.setExchange(resultSet.getBoolean("exchange"));
        return slot;
    }
}
