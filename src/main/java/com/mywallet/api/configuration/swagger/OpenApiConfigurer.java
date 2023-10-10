package com.mywallet.api.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.mywallet.api.provider.constrans.Environment.OPEN_API_BEARER_TYPE;

@Profile(value = {"dev"})
@Configuration
@OpenAPIDefinition(info = @Info(
        version = "${app.version}",
        description = "${app.version}",
        title = "${app.title}"))
public class OpenApiConfigurer {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(OPEN_API_BEARER_TYPE,
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

}
