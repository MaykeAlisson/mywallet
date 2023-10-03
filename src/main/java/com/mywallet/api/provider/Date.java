package com.mywallet.api.provider;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Date {

    public static boolean isValida(final LocalDateTime value) {
        return value != null;
    }

    public static java.util.Date toDate(final LocalDateTime localDateTime) {
        return localDateTime != null ? java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
