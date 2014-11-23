package cucumber.blueprint.jvm.example.integration.cucumber.step.common

import org.apache.camel.{CamelContext, ProducerTemplate}
import cucumber.blueprint.jvm.example.infrastructure.integration.camel.ExampleOsgiService
import javax.inject.Inject
import cucumber.runtime.java.blueprint.BlueprintDescriptorLocation

@BlueprintDescriptorLocation(value = "/OSGI-INF/blueprint/blueprintContext.xml")
class BaseStepDefinition {
  @Inject
  protected var producerTemplate: ProducerTemplate = null

  @Inject
  protected var camelContext: CamelContext = null

  @Inject
  protected var exampleOsgiService: ExampleOsgiService = null

  protected var result: String = _
}
