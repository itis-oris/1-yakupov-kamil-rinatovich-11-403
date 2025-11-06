package org.example.orissem01.services;

import jakarta.servlet.http.HttpServletRequest;
import org.example.orissem01.models.Record;
import org.example.orissem01.models.Slot;
import org.example.orissem01.models.Transaction;
import org.example.orissem01.models.User;
import org.example.orissem01.repositories.TransactionRepository;

public class TransactonService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final RecordService recordService;

    public TransactonService(){
        this.transactionRepository = new TransactionRepository();
        this.userService = new UserService();
        this.recordService = new RecordService();
    }

    public void addTransaction(HttpServletRequest request){
        Transaction transaction = mapTransaction(request);
        try {
            transactionRepository.addTransaction(transaction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction mapTransaction(HttpServletRequest request) {
        Transaction transaction = new Transaction();

        Long recordId = Long.parseLong(request.getParameter("choosedRecordId"));
        Record record = recordService.findRecordById(recordId);
        User fromUser = record.getUser();
        Slot slot = record.getSlot();

        User toUser = userService.findUserByLogin(request);

        String comment = request.getParameter("comment");

        transaction.setFromUser(fromUser);
        transaction.setToUser(toUser);
        transaction.setSlot(slot);
        transaction.setComment(comment);

        return transaction;
    }

}
