package com.mywallet.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import static com.mywallet.api.provider.constrans.Environment.DEV;
import static com.mywallet.api.provider.constrans.Environment.SPRING_PROFILES_ACTIVE;
import static java.lang.System.getenv;
import static java.lang.System.setProperty;
import static java.util.Optional.ofNullable;
import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

//@SpringBootApplication
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ApiApplication {

	public static void main(String[] args) {
		setProperty(ACTIVE_PROFILES_PROPERTY_NAME, ofNullable(getenv(SPRING_PROFILES_ACTIVE)).orElse(DEV));
		SpringApplication.run(ApiApplication.class, args);
	}

}
