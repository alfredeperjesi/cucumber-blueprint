package cucumber.blueprint.jvm.example.infrastructure.integration.camel;

public class ExampleOsgiService {

    public static final String HELLO_TEMPLATE = "Hello %s!";

    public String greet(final String name) {
        return String.format(HELLO_TEMPLATE, name);
    }
}
