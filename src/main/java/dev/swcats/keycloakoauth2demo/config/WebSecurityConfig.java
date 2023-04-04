package dev.swcats.keycloakoauth2demo.config;


import dev.swcats.keycloakoauth2demo.handler.KeycloakLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    final KeycloakLogoutHandler keycloakLogoutHandler;
    WebSecurityConfig(KeycloakLogoutHandler logoutHandler){
        this.keycloakLogoutHandler = logoutHandler;
    }

    @Bean
    SecurityFilterChain clientSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.authorizeHttpRequests()
                .requestMatchers("/","/login**")
                .permitAll()
                .anyRequest().authenticated().and().oauth2Login().and().logout().addLogoutHandler(keycloakLogoutHandler);
        // @formatter:on
        return http.build();
    }

}