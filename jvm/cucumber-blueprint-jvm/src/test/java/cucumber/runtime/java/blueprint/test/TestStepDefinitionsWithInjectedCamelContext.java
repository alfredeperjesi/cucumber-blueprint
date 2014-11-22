package cucumber.runtime.java.blueprint.test;

import cucumber.runtime.java.blueprint.BlueprintDescriptorLocation;
import org.apache.camel.CamelContext;

import javax.inject.Inject;

@BlueprintDescriptorLocation("OSGI-INF/blueprint/blueprintContext.xml")
public class TestStepDefinitionsWithInjectedCamelContext {
    @Inject
    private CamelContext camelContext;

    public CamelContext getCamelContext() {
        return camelContext;
    }
}
