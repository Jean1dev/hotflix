package com.hotflix.admin.infra.config;

import com.hotflix.admin.infra.config.annotations.VideoCreatedQueue;
import com.hotflix.admin.infra.config.properties.amqp.QueueProperties;
import com.hotflix.admin.infra.services.EventService;
import com.hotflix.admin.infra.services.impl.RabbitEventService;
import com.hotflix.admin.infra.services.local.InMemoryEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EventConfig {

    @Bean
    @VideoCreatedQueue
    @Profile({"sandbox"})
    EventService localVideoCreatedEventService() {
        return new InMemoryEventService();
    }

    @Bean
    @VideoCreatedQueue
    @ConditionalOnMissingBean
    EventService videoCreatedEventService(
            @VideoCreatedQueue final QueueProperties props,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
