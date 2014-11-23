package cucumber.blueprint.jvm.example.integration.cucumber.scan;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/exampleroute.feature", glue = {
        "cucumber.blueprint.jvm.example.integration.cucumber.step.exampleroute"}, strict = true)
public class ExampleRouteBuilderITest {
}
