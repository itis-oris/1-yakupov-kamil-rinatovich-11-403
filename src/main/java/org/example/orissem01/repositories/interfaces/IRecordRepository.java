package org.example.orissem01.repositories.interfaces;

import org.example.orissem01.models.Record;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRecordRepository {

    Optional<Record> findRecordById(Long id) throws SQLException, ClassNotFoundException;

    List<Record> getRecords() throws SQLException, ClassNotFoundException;

    void updateRecord(Record record) throws SQLException, ClassNotFoundException;

    Record mapRecord(ResultSet resultSet) throws SQLException, ClassNotFoundException;

}
