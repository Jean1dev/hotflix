package com.hotflix.admin;

import com.hotflix.admin.infra.config.WebServerConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
@SpringBootTest(classes = WebServerConfiguration.class)
@ExtendWith(MySQLCleanUpExtension.class)
@Tag("integrationTest")
public @interface IntegrationTest {
}
