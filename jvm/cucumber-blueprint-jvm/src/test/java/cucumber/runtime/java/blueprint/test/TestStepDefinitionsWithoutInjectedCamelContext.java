package cucumber.runtime.java.blueprint.test;

import cucumber.runtime.java.blueprint.BlueprintDescriptorLocation;
import org.apache.camel.CamelContext;

@BlueprintDescriptorLocation("OSGI-INF/blueprint/blueprintContext.xml")
public class TestStepDefinitionsWithoutInjectedCamelContext {
    private CamelContext camelContext;

    public CamelContext getCamelContext() {
        return camelContext;
    }
}
