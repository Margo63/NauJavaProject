package ru.margarita.NauJava.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.margarita.NauJava.domain.UserDetailServiceImpl;

import java.util.Collection;

/**
 * Класс конфигурации SpringSecurity
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-11-11
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig
{
    final private String ADMIN = "ADMIN";
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz

                        .requestMatchers("/login", "/registration").permitAll()
                        .requestMatchers("/swagger-ui/**","/admin/**").hasRole(ADMIN)
                        .requestMatchers("/custom/users/**").hasAnyRole(ADMIN, "USER")
                        .requestMatchers("/reports/**").hasRole(ADMIN)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            // роли пользователя
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                            String redirectUrl = "/custom/users/view/user"; // значение по умолчанию

                            // Проверка роли
                            boolean isAdmin = authorities.stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                            boolean isUser = authorities.stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

                            if (isAdmin) {
                                redirectUrl = "/admin/view/mainPage";
                            }

                            // Редирект
                            response.sendRedirect(redirectUrl);
                        })
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)

        ;
        http.csrf().disable();
        return http.build();

    }
}