package com.app;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class CamelTest extends CamelTestSupport {
    @Test
    public void testHttpInterceptSendToEndpoint() throws Exception {
        RouteDefinition route = context.getRouteDefinitions().get(0);
        route.adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("direct:sth").to("mock:http").skipSendToOriginalEndpoint();
            }
        });

        MockEndpoint mockEndpoint = getMockEndpoint("mock:http");
        mockEndpoint.expectedMessageCount(1);

        template.requestBody("http://localhost:8085/hello?name=World", "S");

        assertMockEndpointsSatisfied();
        Output output = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Output.class);

        assertNotNull(output);
        assertEquals("Hello World", output.getMessage());
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RestDslRouteBuilder();
    }
}
