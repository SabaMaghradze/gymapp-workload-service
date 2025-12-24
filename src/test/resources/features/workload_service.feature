Feature: Workload Service component tests

  Scenario: Process valid workload
    Given a valid workload request
    When I POST it to "/api/trainer/workload"
    Then the response status should be 200

  Scenario: Process invalid workload with wrong action type
    Given an invalid workload request with action type "EEE"
    When I POST it to "/api/trainer/workload"
    Then the response status should be 400