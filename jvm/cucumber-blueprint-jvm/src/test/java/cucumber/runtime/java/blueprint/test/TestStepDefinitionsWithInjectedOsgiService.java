package cucumber.runtime.java.blueprint.test;

import cucumber.runtime.java.blueprint.BlueprintDescriptorLocation;

import javax.inject.Inject;

@BlueprintDescriptorLocation("OSGI-INF/blueprint/blueprintContext.xml")
public class TestStepDefinitionsWithInjectedOsgiService {
    @Inject
    private TestOsgiService camelContext;

    public TestOsgiService getOsgiService() {
        return camelContext;
    }
}
