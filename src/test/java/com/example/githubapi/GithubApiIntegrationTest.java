package com.example.githubapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubApiIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/github";
    }

    @Test
    void shouldReturn404ForNonExistentUser() {
        try {
            restTemplate.getForEntity(getBaseUrl() + "/users/nonexistentuser/repositories", String.class);
        } catch (HttpClientErrorException ex) {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(ex.getResponseBodyAsString()).contains("\"status\":404");
        }
    }



    @Test
    void shouldHandleInvalidUrlGracefully() {
        try {
            restTemplate.getForEntity(getBaseUrl() + "/invalidEndpoint", String.class);
        } catch (HttpClientErrorException ex) {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }



    @Test
    void shouldNotExposeSensitiveInformationOnErrors() {
        try {
            restTemplate.getForEntity(getBaseUrl() + "/users/nonexistentuser/repositories", String.class);
        } catch (HttpClientErrorException ex) {
            String responseBody = ex.getResponseBodyAsString();
            assertThat(responseBody).doesNotContain("Exception");
            assertThat(responseBody).doesNotContain("stackTrace");
        }
    }
}
