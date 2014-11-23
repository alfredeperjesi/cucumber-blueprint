package cucumber.blueprint.jvm.example.integration.cucumber.scan;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/exampleosgiservice.feature", glue = {
        "cucumber.blueprint.jvm.example.integration.cucumber.step.exampleosgiservice"}, strict = true)
public class ExampleOsgiServiceITest {
}
