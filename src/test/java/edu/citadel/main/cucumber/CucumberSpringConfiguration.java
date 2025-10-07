package edu.citadel.main.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

// For HW2
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfiguration {
    // Status Endpoints
    public void theClientCallsHealth() {}
    public void theClientReceivesStatusCodeOf(int statusCode) {}
    public void theClientReceivesServerStatusStringOk() {}
    public void theClientCallsInfo() {}
    public void theClientReceivesAppName(String expectedName) {}
    public void theClientReceivesAppVersion(String expectedVersion) {}
    public void theClientReceivesAppDescription(String expectedDescription) {}

    // Account Endpoints
    public void theClientPostsToWithBody(String path, String body) {}
    public void theClientReceivesTheCreatedAccount(String username, String email) throws Exception {}
}
