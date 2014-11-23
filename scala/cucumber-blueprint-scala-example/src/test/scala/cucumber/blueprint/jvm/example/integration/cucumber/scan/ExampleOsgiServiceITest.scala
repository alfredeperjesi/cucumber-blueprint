package cucumber.blueprint.jvm.example.integration.cucumber.scan

import org.junit.runner.RunWith
import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber

@RunWith(classOf[Cucumber])
@CucumberOptions(features = Array("classpath:features/exampleosgiservice.feature"), glue = Array("cucumber.blueprint.jvm.example.integration.cucumber.scan.exampleosgiservice"), strict = true)
class ExampleOsgiServiceITest
