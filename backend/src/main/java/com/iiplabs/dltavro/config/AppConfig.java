package com.iiplabs.dltavro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class AppConfig {

    @Bean("validator")
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

}
