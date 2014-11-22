package cucumber.blueprint.jvm.example.integration.cucumber.step.exampleroute;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.blueprint.jvm.example.integration.cucumber.step.common.BaseStepDefinition;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExampleRouteStepDefinitions extends BaseStepDefinition {
    private String result;

    @When("call the example route")
    public void callRest() throws Exception {
        assertThat(camelContext, notNullValue());
        assertThat(exampleOsgiService, notNullValue());
        result = producerTemplate.requestBody("direct:test", "", String.class);
    }

    @Then("the response is (.+)")
    public void verifyExampleRouteResponse(final String response) {
        assertThat(result, equalTo(response));
    }
}
