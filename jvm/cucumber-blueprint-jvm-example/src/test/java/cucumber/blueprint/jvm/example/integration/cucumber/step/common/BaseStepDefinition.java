package cucumber.blueprint.jvm.example.integration.cucumber.step.common;

import cucumber.blueprint.jvm.example.infrastructure.integration.camel.ExampleOsgiService;
import cucumber.runtime.java.blueprint.BlueprintDescriptorLocation;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import javax.inject.Inject;

@BlueprintDescriptorLocation(value = "/OSGI-INF/blueprint/blueprintContext.xml")
public abstract class BaseStepDefinition {

    @Inject
    protected ProducerTemplate producerTemplate;

    @Inject
    protected CamelContext camelContext;

    @Inject
    protected ExampleOsgiService exampleOsgiService;
}
