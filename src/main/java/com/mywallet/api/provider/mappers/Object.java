package com.mywallet.api.provider.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Object {

    public static <T> T readValue(final String fromValue, final Class<T> toValueType) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(fromValue, toValueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
