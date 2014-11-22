package cucumber.blueprint.jvm.example.infrastructure.integration.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultMessage;

public class ExampleRouteBuilder extends RouteBuilder {

    private static final String TEST_IS_SUCCESS = "Test is success";

    @Override
    public void configure() throws Exception {
        from("direct:test")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        DefaultMessage out = new DefaultMessage();
                        out.setBody(TEST_IS_SUCCESS, String.class);
                        exchange.setOut(out);
                    }
                });
    }
}
