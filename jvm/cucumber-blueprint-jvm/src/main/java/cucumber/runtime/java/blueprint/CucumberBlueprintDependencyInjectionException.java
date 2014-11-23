package cucumber.runtime.java.blueprint;

public class CucumberBlueprintDependencyInjectionException extends RuntimeException {
    public CucumberBlueprintDependencyInjectionException(final String message) {
        super(message);
    }

    public CucumberBlueprintDependencyInjectionException(final String message, final Exception cause) {
        super(message, cause);
    }
}
