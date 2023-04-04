package dev.swcats.keycloakoauth2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ConfigurationPropertiesScan
public class KeycloakOauth2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeycloakOauth2DemoApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
