package dev.swcats.keycloakoauth2demo.testcontainers;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
public class KeycloakContainersTest {

    @Autowired
    private MockMvc mockMvc;

    // container {
    @Container
    public static KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:21.0")
            .withExposedPorts(8080)
            .withRealmImportFile("keycloak/realm-export.json")
            .withStartupTimeout(Duration.of(2, ChronoUnit.MINUTES))
            .waitingFor(Wait.forHttp("/"));

    // }

    @BeforeAll
    static void beforeAll() {

        keycloak.start();
        RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("application/x-www-form-urlencoded", ContentType.URLENC));
    }

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = keycloak.getAuthServerUrl();
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "realms/app-login");
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                () -> keycloak.getAuthServerUrl() + "realms/app-login/protocol/openid-connect/certs");
        registry.add("spring.security.oauth2.client.provider.keycloak.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "realms/app-login");
    }

    @Test
    public void testAdminUsername() {
        System.out.println(keycloak.getAuthServerUrl());
        Assertions.assertThat(keycloak.getAdminUsername()).isEqualTo("admin");
    }

    @Test
    public void testPageRequiresRequiresPostMethod(){
        RestAssured.given().header("Authorization", "1")
                .when()
                .get("/realms/app-login/protocol/openid-connect/token")
                .then()
                .statusCode(405);
    }

    @Test
    public void testUserObtainToken() throws JSONException {
        String formData = "grant_type=password&client_id=app-login&username=sam&password=123123";

        RestAssured.given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(formData)
                .post("/realms/app-login/protocol/openid-connect/token")
                .then()
                .statusCode(200);
    }

    @Test
    public void testAdminObtainToken() throws JSONException {
        String formData = "grant_type=password&client_id=app-login&username=petrovich&password=321321";

        RestAssured.given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(formData)
                .post("/realms/app-login/protocol/openid-connect/token")
                .then()
                .statusCode(200);
    }

    @Test
    public void testPageRequiresAuthentication() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/somebody"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/oauth2/authorization/keycloak"));
    }
}
