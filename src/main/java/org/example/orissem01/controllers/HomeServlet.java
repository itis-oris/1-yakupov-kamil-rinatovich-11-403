package org.example.orissem01.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.orissem01.services.RecordService;
import org.example.orissem01.services.TransactonService;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private final RecordService recordService = new RecordService();
    private final TransactonService transactonService = new TransactonService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String resource = recordService.getExchangedRecords(request);
        request.getRequestDispatcher(resource).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("choosedRecordId") != null) {
            transactonService.addTransaction(request);
        }
        doGet(request, response);
    }
}
