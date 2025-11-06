package org.example.orissem01;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //System.out.println(bCryptPasswordEncoder.encode("kibersashka"));
        //System.out.println(bCryptPasswordEncoder.matches("qwertyuiop10", "$2a$10$u/lhLt6gSf1y/jqkOgwvweHA.SuXQLFErHWeI1eimBr1p/EtyjqHm"));

        String date = LocalTime.now().toString();
        System.out.println(date);
        LocalTime time = LocalTime.now();
        LocalTime time1 = LocalTime.parse("12:00:00");
        LocalDate date1 = LocalDate.parse("2025-11-03");

    }
}
