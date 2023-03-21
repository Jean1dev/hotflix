package com.hotflix.admin.infra.config;

import com.hotflix.admin.infra.config.annotations.VideoCreatedQueue;
import com.hotflix.admin.infra.config.properties.amqp.QueueProperties;
import com.hotflix.admin.infra.services.EventService;
import com.hotflix.admin.infra.services.impl.RabbitEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    @VideoCreatedQueue
    EventService videoCreatedEventService(
            @VideoCreatedQueue final QueueProperties props,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
