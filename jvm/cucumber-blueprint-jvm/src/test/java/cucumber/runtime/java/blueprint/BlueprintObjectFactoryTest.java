package cucumber.runtime.java.blueprint;

import cucumber.runtime.CucumberException;
import cucumber.runtime.java.blueprint.test.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class BlueprintObjectFactoryTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BlueprintObjectFactory blueprintObjectFactory;

    @Before
    public void setUp() {
        blueprintObjectFactory = new BlueprintObjectFactory();
    }

    @Test
    public void getInstanceThrowsExceptionWhenNoInstanceFound() {
        expectedException.expect(CucumberException.class);
        expectedException.expectMessage("Step definitions instance is not found for class cucumber.runtime.java.blueprint.test.TestStepDefinitionsThrowsExceptionFromDefaultContructor");

        blueprintObjectFactory.getInstance(TestStepDefinitionsThrowsExceptionFromDefaultContructor.class);
    }

    @Test
    public void addClassCreatesInstanceForStepDefinitionsWithoutBlueprintDescriptors() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithoutBlueprintDescriptors.class);

        Object addedInstance = blueprintObjectFactory.getInstance(TestStepDefinitionsWithoutBlueprintDescriptors.class);

        assertThat(addedInstance, notNullValue());
    }

    @Test
    public void addClassCreatesOnlyOneInstanceForStepDefinitionsWithoutBlueprintDescriptorsWhenAddedTwice() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithoutBlueprintDescriptors.class);
        blueprintObjectFactory.addClass(TestStepDefinitionsWithoutBlueprintDescriptors.class);

        Object addedInstance = blueprintObjectFactory.getInstance(TestStepDefinitionsWithoutBlueprintDescriptors.class);

        assertThat(addedInstance, notNullValue());
    }

    @Test
    public void addClassCreatesInstanceForStepDefinitionsWithOneBlueprintDescriptor() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithOneBlueprintDescriptors.class);

        Object addedInstance = blueprintObjectFactory.getInstance(TestStepDefinitionsWithOneBlueprintDescriptors.class);

        assertThat(addedInstance, notNullValue());
    }

    @Test
    public void addClassCreatesInstanceForStepDefinitionsWithEmptyBlueprintDescriptor() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithOneBlueprintDescriptors.class);

        Object addedInstance = blueprintObjectFactory.getInstance(TestStepDefinitionsWithOneBlueprintDescriptors.class);

        assertThat(addedInstance, notNullValue());
    }

    @Test
    public void addClassCreatesOnlyOneInstanceForStepDefinitionsWithEmptyBlueprintDescriptorWhenAddedTwice() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithOneBlueprintDescriptors.class);

        Object addedInstance = blueprintObjectFactory.getInstance(TestStepDefinitionsWithOneBlueprintDescriptors.class);

        assertThat(addedInstance, notNullValue());
    }

    @Test
    public void addClassCreatesOnlyOneInstanceForStepDefinitionsWithOneBlueprintDescriptorWhenAddedTwice() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithOneBlueprintDescriptors.class);
        blueprintObjectFactory.addClass(TestStepDefinitionsWithOneBlueprintDescriptors.class);

        Object addedInstance = blueprintObjectFactory.getInstance(TestStepDefinitionsWithOneBlueprintDescriptors.class);

        assertThat(addedInstance, notNullValue());
    }

    @Test
    public void addClassCreatesTwoInstanceForTwoStepDefinitionsWithTheSameBlueprintDescriptors() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithOneBlueprintDescriptors.class);
        blueprintObjectFactory.addClass(TestStepDefinitions2WithOneBlueprintDescriptors.class);

        Object addedInstance = blueprintObjectFactory.getInstance(TestStepDefinitions2WithOneBlueprintDescriptors.class);

        assertThat(addedInstance, notNullValue());
        addedInstance = blueprintObjectFactory.getInstance(TestStepDefinitions2WithOneBlueprintDescriptors.class);

        assertThat(addedInstance, notNullValue());
    }

    @Test
    public void addClassThrowsExceptionWhenTheBlueprintDescriptorsAreDifferent() {
        expectedException.expect(CucumberBlueprintContextException.class);
        expectedException.expectMessage("Mismatched blueprint descriptors on step definitions: cucumber.runtime.java.blueprint.test.TestStepDefinitionsWithTwoBlueprintDescriptors and cucumber.runtime.java.blueprint.test.TestStepDefinitionsWithOneBlueprintDescriptors");

        blueprintObjectFactory.addClass(TestStepDefinitionsWithOneBlueprintDescriptors.class);
        blueprintObjectFactory.addClass(TestStepDefinitionsWithTwoBlueprintDescriptors.class);
    }

    @Test
    public void addClassThrowsExceptionWhenTheBlueprintDescriptorsAreDifferentInverseOrder() {
        expectedException.expect(CucumberBlueprintContextException.class);
        expectedException.expectMessage("Mismatched blueprint descriptors on step definitions: cucumber.runtime.java.blueprint.test.TestStepDefinitionsWithOneBlueprintDescriptors and cucumber.runtime.java.blueprint.test.TestStepDefinitionsWithTwoBlueprintDescriptors");

        blueprintObjectFactory.addClass(TestStepDefinitionsWithTwoBlueprintDescriptors.class);
        blueprintObjectFactory.addClass(TestStepDefinitionsWithOneBlueprintDescriptors.class);
    }

    @Test
    public void addClassThrowsExceptionWhenThereIsNoDefaultConstructor() {
        expectedException.expect(CucumberException.class);
        expectedException.expectMessage("cucumber.runtime.java.blueprint.test.TestStepDefinitionsWithoutDefaultConstructor doesn't have an empty constructor");

        blueprintObjectFactory.addClass(TestStepDefinitionsWithoutDefaultConstructor.class);
    }

    @Test
    public void addClassThrowsExceptionWhenInstanceCreationThrowsException() {
        expectedException.expect(CucumberException.class);
        expectedException.expectMessage("Failed to instantiate cucumber.runtime.java.blueprint.test.TestStepDefinitionsThrowsExceptionFromDefaultContructor");

        blueprintObjectFactory.addClass(TestStepDefinitionsThrowsExceptionFromDefaultContructor.class);
    }

    @Test
    public void startDoesNotSetupBlueprintContextWhenNoBlueprintContext() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithoutBlueprintDescriptors.class);

        blueprintObjectFactory.start();
    }

    @Test
    public void startThrowsExceptionWhenBlueprintContextSetUpFails() {
        expectedException.expect(CucumberBlueprintContextException.class);
        expectedException.expectMessage("Blueprint context initialisation failed by descriptiors");

        blueprintObjectFactory.addClass(TestStepDefinitionsWithEmptyBlueprintDescriptors.class);

        blueprintObjectFactory.start();
    }

    @Test
    public void startInjectCamelContext() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithInjectedCamelContext.class);

        blueprintObjectFactory.start();

        TestStepDefinitionsWithInjectedCamelContext stepDefinitions = blueprintObjectFactory.getInstance(TestStepDefinitionsWithInjectedCamelContext.class);

        assertThat(stepDefinitions.getCamelContext(), notNullValue());
    }

    @Test
    public void startInjectProducerTemplate() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithInjectedProducerTemplate.class);

        blueprintObjectFactory.start();

        TestStepDefinitionsWithInjectedProducerTemplate stepDefinitions = blueprintObjectFactory.getInstance(TestStepDefinitionsWithInjectedProducerTemplate.class);

        assertThat(stepDefinitions.getProducerTemplate(), notNullValue());
    }

    @Test
    public void startInjectOsgiService() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithInjectedOsgiService.class);

        blueprintObjectFactory.start();

        TestStepDefinitionsWithInjectedOsgiService stepDefinitions = blueprintObjectFactory.getInstance(TestStepDefinitionsWithInjectedOsgiService.class);

        assertThat(stepDefinitions.getOsgiService(), notNullValue());
    }

    @Test
    public void startInjectDependencyFromSuperClass() {
        blueprintObjectFactory.addClass(TestStepDefinitionsExtendingBaseClassWithInjectedCamelContext.class);

        blueprintObjectFactory.start();

        TestStepDefinitionsExtendingBaseClassWithInjectedCamelContext stepDefinitions = blueprintObjectFactory.getInstance(TestStepDefinitionsExtendingBaseClassWithInjectedCamelContext.class);

        assertThat(stepDefinitions.getCamelContext(), notNullValue());
    }

    @Test
    public void startDoesNotInjectDependencyWhenInjectAnnotationIsAbsent() {
        blueprintObjectFactory.addClass(TestStepDefinitionsWithoutInjectedCamelContext.class);

        blueprintObjectFactory.start();

        TestStepDefinitionsWithoutInjectedCamelContext stepDefinitions = blueprintObjectFactory.getInstance(TestStepDefinitionsWithoutInjectedCamelContext.class);

        assertThat(stepDefinitions.getCamelContext(), nullValue());
    }

    @Test
    public void stopThrowsExceptionWhenBlueprintContextTearDownFailing() throws NoSuchFieldException, IllegalAccessException {
        expectedException.expect(CucumberBlueprintContextException.class);
        expectedException.expectMessage("Error during tear dow");

        Field stepDefinitionsInstances = BlueprintObjectFactory.class.getDeclaredField("stepDefinitionsInstances");
        stepDefinitionsInstances.setAccessible(true);
        stepDefinitionsInstances.set(blueprintObjectFactory, null);

        blueprintObjectFactory.stop();
    }

    @Test
    public void stopTearsDownTheBlueprintContext() throws NoSuchFieldException, IllegalAccessException {
        expectedException.expect(CucumberException.class);
        expectedException.expectMessage("Step definitions instance is not found for class cucumber.runtime.java.blueprint.test.TestStepDefinitionsWithoutInjectedCamelContext");
        blueprintObjectFactory.addClass(TestStepDefinitionsWithoutInjectedCamelContext.class);

        blueprintObjectFactory.start();

        blueprintObjectFactory.stop();

        TestStepDefinitionsWithoutInjectedCamelContext stepDefinitions = blueprintObjectFactory.getInstance(TestStepDefinitionsWithoutInjectedCamelContext.class);

        assertThat(stepDefinitions, nullValue());
    }

}
