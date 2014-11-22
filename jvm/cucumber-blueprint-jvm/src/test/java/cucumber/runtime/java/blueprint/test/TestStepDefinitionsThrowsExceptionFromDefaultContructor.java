package cucumber.runtime.java.blueprint.test;

public class TestStepDefinitionsThrowsExceptionFromDefaultContructor {
    public TestStepDefinitionsThrowsExceptionFromDefaultContructor() {
        throw new RuntimeException("Error");
    }
}
