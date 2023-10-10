package com.mywallet.api;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.mywallet.api.provider.constrans.Environment.ROLE_BASIC;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
@Transactional
@WithMockUser(roles = ROLE_BASIC)
public abstract class ApiApplicationTests {

}
