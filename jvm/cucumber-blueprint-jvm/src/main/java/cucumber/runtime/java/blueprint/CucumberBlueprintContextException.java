package cucumber.runtime.java.blueprint;

public class CucumberBlueprintContextException extends RuntimeException {
    public CucumberBlueprintContextException(final String message, final Exception cause) {
        super(message, cause);
    }

    public CucumberBlueprintContextException(final String message) {
        super(message);
    }
}
