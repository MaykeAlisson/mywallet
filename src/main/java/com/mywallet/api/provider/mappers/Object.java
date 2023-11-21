package com.mywallet.api.provider.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class Object {

    public static <T> T readValue(final String fromValue, final Class<T> toValueType) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(fromValue, toValueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(final InputStream src, final Class<T> valueType) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(src, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
