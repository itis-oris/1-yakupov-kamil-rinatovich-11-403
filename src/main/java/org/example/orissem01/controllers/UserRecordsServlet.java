package org.example.orissem01.controllers;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.orissem01.exceptions.ConnectionException;
import org.example.orissem01.exceptions.MySQLException;
import org.example.orissem01.models.Record;
import org.example.orissem01.models.User;
import org.example.orissem01.services.RecordService;
import org.example.orissem01.services.UserService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/user/records")
public class UserRecordsServlet extends HttpServlet {

    private UserService userService;
    private RecordService recordService;

    @Override
    public void init(){
        ServletContext servletContext = getServletContext();
        this.userService = (UserService) servletContext.getAttribute("userService");
        this.recordService = (RecordService) servletContext.getAttribute("recordService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.findUserByLogin(request);
        String recordsType = getRecordsType(request);
        List<Record> records;
        if (recordsType != null && !(records = recordService.getUserRecords(recordsType, user)).isEmpty()) {
            request.setAttribute("records", records);
        }
        request.setAttribute("context", request.getContextPath());
        request.getRequestDispatcher("/userRecords.ftl").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.findUserByLogin(request);
        String recordId = request.getParameter("choosedRecordId").split(";")[0];
        String recordsType = request.getParameter("choosedRecordId").split(";")[1];
        Long id = Long.parseLong(recordId);
        try {
            recordService.updateUserRecord(user, recordsType, id);
            String resource = "/user/records" + "?recordsType=" + URLEncoder.encode(recordsType, StandardCharsets.UTF_8);
            response.sendRedirect(String.format("%s%s", request.getContextPath(), resource));
        } catch (MySQLException | ConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRecordsType(HttpServletRequest request) {
        String recordsType = request.getParameter("recordsType");
        if (recordsType == null && request.getAttribute("recordsType") != null) {
            recordsType = request.getAttribute("recordsType").toString();
        }
        request.setAttribute("recordsType", recordsType);
        return recordsType;
    }
}