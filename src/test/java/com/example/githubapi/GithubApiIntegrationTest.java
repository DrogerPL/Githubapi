package com.example.githubapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubApiIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void shouldReturnRepositoriesForValidUser() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/github/octocat/repos", String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void shouldReturn404ForNonExistentUser() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/github/nonexistentuser/repos", String.class);
            assertThat(response.getBody()).isEmpty();
        } catch (HttpClientErrorException ex) {
            System.out.println("Exception caught: " + ex.getMessage());
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }


}
