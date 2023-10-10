package com.mywallet.api.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

@Configuration
public class ApplicationConfig {

    @Bean
    public LocaleResolver localeResolver() {
        FixedLocaleResolver lr = new FixedLocaleResolver();
        lr.setDefaultLocale(Locale.US);
        return lr;
    }
}
