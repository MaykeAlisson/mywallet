package com.mywallet.api.crontrollers;

import com.mywallet.api.ApiApplicationTests;
import com.mywallet.api.domain.model.AcessModel;
import com.mywallet.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.mywallet.api.mock.UserMock.JSON_USER_CREATE_REQUEST;
import static com.mywallet.api.provider.mappers.Object.readValue;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerTest extends ApiApplicationTests {

    String URL_USERS = "/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void create() throws Exception {
        final var acess = new AcessModel(1, "any_name", "token");

        when(this.userService.create(any())).thenReturn(acess);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JSON_USER_CREATE_REQUEST);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        final var responseBody = readValue(response.getContentAsString(), AcessModel.class);

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(responseBody.id(), acess.id());
        assertEquals(responseBody.name(), acess.name());
        assertEquals(responseBody.token(), acess.token());
    }

    @Test
    void update() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(format("%s/1", URL_USERS))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JSON_USER_CREATE_REQUEST);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
