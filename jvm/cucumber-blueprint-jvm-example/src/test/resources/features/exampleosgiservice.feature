Feature: Example osgi service

  Scenario: Call the example osgi service
    When call the greet service by XYZ
    Then the greeting is Hello XYZ!