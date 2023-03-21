package com.hotflix.admin.infra;

import com.hotflix.admin.infra.config.WebServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(WebServerConfiguration.class, args);
    }
}