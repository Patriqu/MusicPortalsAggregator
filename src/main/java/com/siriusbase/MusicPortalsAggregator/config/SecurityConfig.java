package com.siriusbase.MusicPortalsAggregator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Profile("prod")
public class SecurityConfig {
    @Bean
    UserDetailsService userDetailsService() {
        var userDetails = User.builder()
                .username("admin")
                .password("{noop}ab")
                .roles("admin")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }
}
