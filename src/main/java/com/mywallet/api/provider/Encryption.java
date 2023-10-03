package com.mywallet.api.provider;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryption {

    public static String encriptar(final String texto) {
        return new BCryptPasswordEncoder().encode(texto);
    }
}
