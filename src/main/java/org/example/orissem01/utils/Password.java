package org.example.orissem01.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Password {

    public static boolean matches(String password, String hashPassword) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        return bCrypt.matches(password, hashPassword);
    }

}
