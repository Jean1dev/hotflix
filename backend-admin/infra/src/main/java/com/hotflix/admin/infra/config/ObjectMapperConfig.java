package com.hotflix.admin.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotflix.admin.infra.config.json.Json;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return Json.mapper();
    }
}
