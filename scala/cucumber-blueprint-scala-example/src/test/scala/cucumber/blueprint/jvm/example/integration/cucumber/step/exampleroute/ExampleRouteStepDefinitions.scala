package cucumber.blueprint.jvm.example.integration.cucumber.step.exampleroute

import cucumber.runtime.java.blueprint.BlueprintDescriptorLocation
import org.hamcrest.MatcherAssert._
import org.hamcrest.CoreMatchers._
import cucumber.api.java.en.{Then, Given}
import cucumber.blueprint.jvm.example.integration.cucumber.step.common.BaseStepDefinition

@BlueprintDescriptorLocation(value = "/OSGI-INF/blueprint/blueprintContext.xml")
class ExampleRouteStepDefinitions extends BaseStepDefinition {

  @Given("^call the example route$")
  def callRoute() {
    assertThat(producerTemplate, notNullValue())
    result = producerTemplate.requestBody("direct:test", "", classOf[String])
  }

  @Then("^the response is (.+)$")
  def verifyExampleRouteResponse(expected: String) {
    assertThat(result, equalTo(expected))
  }
}