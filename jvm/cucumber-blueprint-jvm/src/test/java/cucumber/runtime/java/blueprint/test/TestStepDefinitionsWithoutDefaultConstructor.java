package cucumber.runtime.java.blueprint.test;

import org.apache.camel.ProducerTemplate;

import javax.inject.Inject;

public class TestStepDefinitionsWithoutDefaultConstructor {
    @Inject
    private final ProducerTemplate producerTemplate;

    public TestStepDefinitionsWithoutDefaultConstructor(final ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }
}
