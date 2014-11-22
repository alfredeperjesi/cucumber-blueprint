package cucumber.runtime.java.blueprint.test;

import cucumber.runtime.java.blueprint.BlueprintDescriptorLocation;
import org.apache.camel.ProducerTemplate;

import javax.inject.Inject;

@BlueprintDescriptorLocation("OSGI-INF/blueprint/blueprintContext.xml")
public class TestStepDefinitionsWithInjectedProducerTemplate {
    @Inject
    private ProducerTemplate producerTemplate;

    public ProducerTemplate getProducerTemplate() {
        return producerTemplate;
    }
}
