package com.hotflix.admin;

import com.hotflix.admin.infra.config.WebServerConfiguration;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@SpringBootTest(classes = WebServerConfiguration.class)
@Tag("integrationTest")
public @interface AmqpTest {
}
