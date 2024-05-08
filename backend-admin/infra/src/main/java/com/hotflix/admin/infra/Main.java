package com.hotflix.admin.infra;

import com.hotflix.admin.infra.config.WebServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        overrideEnvironment();
        SpringApplication.run(WebServerConfiguration.class, args);
    }

    private static void overrideEnvironment() {
        String profile = System.getenv("OVERRIDE_PROFILE");
        if (profile == null)
            return;

        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, profile);
    }
}