package cucumber.blueprint.jvm.example.integration.cucumber.step.exampleosgiservice;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.blueprint.jvm.example.integration.cucumber.step.common.BaseStepDefinition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExampleOsgiServiceStepDefinition extends BaseStepDefinition {

    private String result;

    @When("call the greet service by (.+)")
    public void callGreetService(final String name) {
        result = exampleOsgiService.greet(name);
    }

    @Then("the greeting is (.+)")
    public void verifyGreetings(final String greetings) {
        assertThat(result, equalTo(greetings));
    }
}
