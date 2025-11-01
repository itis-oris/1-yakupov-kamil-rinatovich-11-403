package org.example.orissem01;

import org.example.orissem01.exceptions.DublicateLoginException;
import org.example.orissem01.models.User;
import org.example.orissem01.repositories.UserRepositoryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("kibersashka"));
        System.out.println(bCryptPasswordEncoder.matches("qwertyuiop10", "$2a$10$u/lhLt6gSf1y/jqkOgwvweHA.SuXQLFErHWeI1eimBr1p/EtyjqHm"));
    }
}
