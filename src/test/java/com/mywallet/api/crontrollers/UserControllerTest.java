package com.mywallet.api.crontrollers;

import com.mywallet.api.ApiApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.mywallet.api.mock.UserMock.JSON_USER_CREATE_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest extends ApiApplicationTests {

    String URL_USERS = "/users";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JSON_USER_CREATE_REQUEST);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}
