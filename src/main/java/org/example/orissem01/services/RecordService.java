package org.example.orissem01.services;

import jakarta.servlet.http.HttpServletRequest;
import org.example.orissem01.exceptions.NoSuchRecordException;
import org.example.orissem01.models.Record;
import org.example.orissem01.models.User;
import org.example.orissem01.repositories.RecordRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

public class RecordService {
    private final RecordRepository recordRepository;
    private final UserService userService;

    public RecordService() {
        this.recordRepository = new RecordRepository();
        this.userService = new UserService();
    }

    public Record findRecordById(Long id) {
        try {
            return recordRepository.findRecordById(id)
                    .orElseThrow(() -> new NoSuchRecordException("Пользователя с таким логином не существует"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String getExchangedRecords(HttpServletRequest request) {
        String resource = "/home.ftl";
        List<Record> records;
            User user = userService.findUserByLogin(request);
            try {
                records = recordRepository.getRecords()
                        .stream()
                        .filter(r -> r.getStatus().equalsIgnoreCase("отдается")
                                && !r.getUser().equals(user)
                                && !userService.containsSlot(user, r.getSlot().getId())
                                && isTimeEqualOrAfter(r))
                        .toList();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        if (records.isEmpty()) {
            request.setAttribute("isEmptyExchanged", "Нет доступных для обмена смен");
        }
        request.setAttribute("records", records);

        return resource;

    }

    public String getUserRecords(HttpServletRequest request) {
        User user = userService.findUserByLogin(request);
        String recordsType = request.getParameter("recordsType");
        if(recordsType == null && request.getAttribute("recordsType") != null){
            recordsType = request.getAttribute("recordsType").toString();
        }
        if (recordsType != null) {
            try {
                List<Record> records;
                if (recordsType.equals("completed")) {
                    records = getRecords(user, r -> !r.getStatus().equalsIgnoreCase("запланирована")
                                    && !r.getStatus().equalsIgnoreCase("отдается"));
                    request.setAttribute("tableName", "Завершенные смены");
                } else if (recordsType.equals("exchanged")) {
                    records = getRecords(user, r -> r.getStatus().equalsIgnoreCase("отдается")
                                    && isTimeEqualOrAfter(r));
                    request.setAttribute("tableName", "Смены для обмена");
                } else {
                    records = getRecords(user, r ->
                                    (r.getStatus().equalsIgnoreCase("запланирована"))
                                            && isTimeEqualOrAfter(r));
                    request.setAttribute("tableName", "Запланированные смены");
                }
                if (records.isEmpty()){
                    request.setAttribute("recordsIsEmpty", "Нет доступных смен");
                } else {
                    request.setAttribute("records", records);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        request.setAttribute("recordsType", recordsType);
        return "/userRecords.ftl";

    }


    public String updateUserRecord(HttpServletRequest request) {
        String resource = "/userRecords.ftl";
        User user = userService.findUserByLogin(request);
        String recordId = request.getParameter("choosedRecordId").split(";")[0];
        String recordsType = request.getParameter("choosedRecordId").split(";")[1];
        Long id = Long.parseLong(recordId);

        List<Record> records;
        try {
            if (recordsType.equals("sheduled")) {
                records =
                        getRecords(user, r -> r.getStatus().equalsIgnoreCase("запланирована"));

                for (Record record : records) {
                    if (record.getId().equals(id)) {
                        record.setStatus("Отдается");
                        recordRepository.updateRecord(record);
                    }
                }

            } else {
                records =
                        getRecords(user, r -> r.getStatus().equalsIgnoreCase("отдается"));

                for (Record record : records) {
                    if (record.getId().equals(id)) {
                        record.setStatus("Запланирована");
                        recordRepository.updateRecord(record);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("recordsType", recordsType);
        return resource;
    }

    private List<Record> getRecords(User user, Predicate<? super Record> predicate) {
        return user.getRecords()
                .stream()
                .filter(predicate)
                .toList();
    }

    private boolean isTimeEqualOrAfter(Record r) {
        return (LocalDate.parse(r.getSlot().getDate()).isAfter(LocalDate.now()))

                ||

                ((LocalDate.parse(r.getSlot().getDate()).isEqual(LocalDate.now()))
                        && (LocalTime.parse(r.getSlot().getTime()).isAfter(LocalTime.now())));
    }
}
