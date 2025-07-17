package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void usersShouldReturnAllUsers() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/users", String.class))
                .contains("Alice", "Bob", "Charlie");
    }

    @Test
    void usersShouldFilterByValidId() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/users?id=1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Alice");
        assertThat(response.getBody()).doesNotContain("Bob", "Charlie");
    }

    @Test
    void usersShouldReturnBadRequestForInvalidId() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/users?id=0", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void usersShouldReturnBadRequestForNegativeId() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/users?id=-1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void usersShouldReturnEmptyForNonExistentId() throws Exception {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:" + port + "/users?id=999", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[]");
    }
}
