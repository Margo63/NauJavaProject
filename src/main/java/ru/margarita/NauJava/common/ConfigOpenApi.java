package ru.margarita.NauJava.common;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Класс конфигурации OpenApi
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-11-11
 */
@EnableAsync
public class ConfigOpenApi {
    @Bean
    public GroupedOpenApi reportApi() {
        return GroupedOpenApi.builder()
                .group("reports")
                .pathsToMatch("/report/**")
                .build();
    }
    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/custom/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi tasksApi() {
        return GroupedOpenApi.builder()
                .group("tasks")
                .pathsToMatch("/tasks/**")
                .build();
    }
}
