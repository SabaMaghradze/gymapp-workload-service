package com.gymapp.workload_service.workload.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WorkloadSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> latestResponse;
    private Map<String, Object> requestPayload;

    @Given("a valid workload request")
    public void a_valid_workload_request() {
        requestPayload = Map.of(
                "trainerUsername", "trainer1",
                "trainerFirstName", "John",
                "trainerLastName", "Doe",
                "isActive", true,
                "trainingDate", LocalDate.now().toString(),
                "duration", 60,
                "actionType", "ADD"
        );
    }

    @Given("an invalid workload request with action type {string}")
    public void an_invalid_workload_request(String actionType) {
        requestPayload = Map.of(
                "trainerUsername", "trainer1",
                "trainerFirstName", "John",
                "trainerLastName", "Doe",
                "isActive", true,
                "trainingDate", LocalDate.now().toString(),
                "duration", 60,
                "actionType", actionType
        );
    }

    @When("I POST it to {string}")
    public void i_post_it_to(String path) {
        latestResponse = restTemplate.postForEntity(path, requestPayload, String.class);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer status) {
        assertThat(latestResponse.getStatusCodeValue()).isEqualTo(status);
    }
}
