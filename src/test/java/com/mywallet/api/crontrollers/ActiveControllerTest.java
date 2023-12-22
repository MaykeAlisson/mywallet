package com.mywallet.api.crontrollers;

import com.mywallet.api.ApiApplicationTests;
import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.domain.enums.ActiveCategory;
import com.mywallet.api.domain.enums.ActiveCurrency;
import com.mywallet.api.domain.enums.ActiveType;
import com.mywallet.api.services.ActiveService;
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

import java.util.List;

import static com.mywallet.api.mock.RequestMock.JSON_ACTIVE_CREATE_REQUEST;
import static com.mywallet.api.provider.mappers.Object.readValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ActiveControllerTest extends ApiApplicationTests {

    String URL_ACTIVES = "/actives";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActiveService activeService;

    @Test
    void create() throws Exception {
        final var active = new Active(1, "any_name", "any_ticket", ActiveCategory.FII, ActiveType.BUY_HOLD, ActiveCurrency.REAL, 3, 2, List.of(), null, null, null);

        when(this.activeService.create(any())).thenReturn(active);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_ACTIVES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JSON_ACTIVE_CREATE_REQUEST);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        final var responseBody = readValue(response.getContentAsString(), Active.class);

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(responseBody.getId(), active.getId());
        assertNull(responseBody.getUser());
    }
}
