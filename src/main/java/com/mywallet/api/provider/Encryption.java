package com.mywallet.api.provider;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {

    public static String encriptar(final String texto) {
        return new BCryptPasswordEncoder().encode(texto);
    }

    public static Boolean check(final String password, final String has){
        return BCrypt.checkpw(password, has);
    }
}
