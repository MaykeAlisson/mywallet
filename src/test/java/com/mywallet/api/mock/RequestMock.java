package com.mywallet.api.mock;

public interface RequestMock {

    String JSON_USER_CREATE_REQUEST = """
               {
                 "name": "Any_Name",
                 "email": "any_email@gmail.com",
                 "password": "123456"
               }
            """;

    String JSON_ACTIVE_CREATE_REQUEST = """
               {
                 "ticket": "MELI34",
                 "quantity": 3,
                 "category": "ACAO",
                 "type": "BUY_HOLD",
                 "currency": "REAL"
               }
            """;
}
