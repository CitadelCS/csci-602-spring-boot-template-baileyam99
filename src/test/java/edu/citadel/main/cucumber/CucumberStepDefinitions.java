package edu.citadel.main.cucumber;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class CucumberStepDefinitions extends CucumberSpringConfiguration {
    @Autowired
    private final TestRestTemplate restTemplate;
    private ResponseEntity<String> latestResponse;
    private String uniqueUsername;
    private String uniqueEmail;

    public CucumberStepDefinitions(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Status Endpoints
    @When("^the client calls /health$")
    public void theClientCallsHealth() {
        latestResponse = restTemplate.getForEntity("/health", String.class);
    }

    @When("^the client calls /info$")
    public void theClientCallsInfo() {
        latestResponse = restTemplate.getForEntity("/info", String.class);
    }

    @Then("the client receives status code of {int}")
    public void theClientReceivesStatusCodeOf(int statusCode) {
        assertThat(latestResponse.getStatusCodeValue()).isEqualTo(statusCode);
    }

    @Then("the client receives server status string ok")
    public void theClientReceivesServerStatusStringOk() {
        assertThat(latestResponse.getBody()).contains("\"status\" : \"ok\"");
    }

    @Then("the client receives app name {string}")
    public void theClientReceivesAppName(String expectedName) {
        assertThat(latestResponse.getBody()).contains("\"name\" : \"" + expectedName + "\"");
    }

    @Then("the client receives app version {string}")
    public void theClientReceivesAppVersion(String expectedVersion) {
        assertThat(latestResponse.getBody()).contains("\"version\" : \"" + expectedVersion + "\"");
    }

    @Then("the client receives app description {string}")
    public void theClientReceivesAppDescription(String expectedDescription) {
        assertThat(latestResponse.getBody()).contains("\"description\" : \"" + expectedDescription + "\"");
    }

    // Account Endpoints
    @When("the client posts to {string} with username {string} and email {string}")
    public void theClientPostsToWithBody(String path, String username, String email) {
        uniqueUsername = username + "_" + System.nanoTime();

        String[] parts = email.split("@");
        uniqueEmail = parts[0] + "_" + System.nanoTime() + "@" + parts[1];

        String body = String.format("""
            {
              "email": "%s",
              "username": "%s",
              "password": "password123"
            }
        """, uniqueEmail, uniqueUsername);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        latestResponse = restTemplate.postForEntity(path, entity, String.class);
    }

    @And("the client receives an account with username {string} and email {string}")
    public void theClientReceivesTheCreatedAccount(String ignoredUsername, String ignoredEmail) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(latestResponse.getBody());

        assertThat(root.get("username").asText()).isEqualTo(uniqueUsername);
        assertThat(root.get("email").asText()).isEqualTo(uniqueEmail);
    }
}
