package com.example.EcommerceFullstack.configuration;

import com.example.EcommerceFullstack.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtFilter;    // Jwt filter for authentication
    private final AuthenticationProvider authProvider;  // Custom AuthenticationProvider

    public SecurityConfig(JwtAuthFilter jwtFilter, AuthenticationProvider authProvider) {
        this.jwtFilter = jwtFilter;
        this.authProvider = authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/test/public", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider) // Custom authentication provider
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)  // Adding JWT filter before UsernamePasswordAuthenticationFilter
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF for JWT APIs

        return http.build();
    }
}

