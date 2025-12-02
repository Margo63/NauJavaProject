package ru.margarita.NauJava.common;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.margarita.NauJava.entities.Task;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс конфигурации списка
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-13
 */
@Configuration
public class ConfigList {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Task> taskContainer()
    {
        return new ArrayList<>();
    }
}
