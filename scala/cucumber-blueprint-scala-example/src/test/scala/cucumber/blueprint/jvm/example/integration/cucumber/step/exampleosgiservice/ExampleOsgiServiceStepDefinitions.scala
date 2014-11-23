package cucumber.blueprint.jvm.example.integration.cucumber.scan.exampleosgiservice

import org.hamcrest.MatcherAssert._
import org.hamcrest.CoreMatchers._
import cucumber.api.java.en.{When, Then}
import cucumber.blueprint.jvm.example.integration.cucumber.step.common.BaseStepDefinition

class ExampleOsgiServiceStepDefinitions extends BaseStepDefinition {

  @When("call the greet service by (.+)")
  def callGreetService(name: String) {
    result = exampleOsgiService.greet(name)
  }

  @Then("the greeting is (.+)")
  def verifyGreetings(greetings: String) {
    assertThat(result, equalTo(greetings))
  }
}