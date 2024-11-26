package org.gklyphon.room.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * Configuration class for Spring Security to set up security rules for the application.
 * It configures CORS (Cross-Origin Resource Sharing) and disables CSRF (Cross-Site Request Forgery) protection.
 * This class also defines public endpoints that are accessible without authentication.
 */
@Configuration
public class SecurityConfig {

    // List of public endpoints that are allowed without authentication.
    private static final String[] PUBLIC_ENDPOINTS = {"/rooms/**", "/rooms/features/**"};

    /**
     * Configures the security filter chain to specify access rules for different HTTP requests.
     * - GET, POST, PUT, DELETE requests for specified public endpoints are permitted without authentication.
     * - Configures CORS with allowed origins, headers, and methods.
     * - Disables CSRF protection.
     *
     * @param http the {@link HttpSecurity} object to configure HTTP security settings.
     * @return a {@link SecurityFilterChain} object that represents the security configuration.
     * @throws Exception if an error occurs while setting up the security configuration.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auths -> auths
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.PUT, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.DELETE, PUBLIC_ENDPOINTS).permitAll()
                )

                .cors( (cors) -> cors
                        .configurationSource(corsConfigurationSource()))

                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    /**
     * Configures the CORS (Cross-Origin Resource Sharing) settings for the application.
     * - Allows requests from "http://localhost:4200".
     * - Allows all headers and specifies allowed HTTP methods (GET, POST, PUT, DELETE).
     * - Sets the max age for preflight requests.
     *
     * @return a {@link CorsConfigurationSource} object containing the CORS configuration.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
