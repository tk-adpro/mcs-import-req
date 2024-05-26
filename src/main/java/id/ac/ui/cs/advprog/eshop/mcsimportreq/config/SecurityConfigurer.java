package id.ac.ui.cs.advprog.eshop.mcsimportreq.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfigurer {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for testing
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/requests/create").permitAll()
                        .requestMatchers("/api/requests/edit/**").permitAll()
                        .requestMatchers("/api/requests/delete/**").permitAll()
                        .requestMatchers("/api/requests/{requestId}").permitAll()
                        .requestMatchers("/api/requests").permitAll()
                        .requestMatchers("/api/requests/status/{requestId}").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Use stateless session management

        return http.build();
    }

}