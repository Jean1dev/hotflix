package com.hotflix.admin.infra.config;

import com.google.cloud.storage.Storage;
import com.hotflix.admin.infra.config.properties.google.GoogleStorageProperties;
import com.hotflix.admin.infra.config.properties.storage.StorageProperties;
import com.hotflix.admin.infra.services.StorageService;
import com.hotflix.admin.infra.services.impl.GCStorageService;
import com.hotflix.admin.infra.services.local.InMemoryStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StorageConfig {

    @Bean
    @ConfigurationProperties(value = "storage.catalogo-videos")
    public StorageProperties storageProperties() {
        return new StorageProperties();
    }

    @Bean
    @Profile({"prod", "dev"})
    public StorageService gcStorageAPI(
            final GoogleStorageProperties props,
            final Storage storage
    ) {
        return new GCStorageService(props.getBucket(), storage);
    }

    @Bean
    @ConditionalOnMissingBean
    public StorageService localStorageAPI() {
        return new InMemoryStorageService();
    }
}
