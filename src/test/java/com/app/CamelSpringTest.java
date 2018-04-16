package com.app;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class CamelSpringTest extends CamelTestSupport {

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

        Object response = template.requestBody("http://localhost:8085/hello?name=World", "S");

        assertMockEndpointsSatisfied();
        Output user = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Output.class);
        System.out.println(user);
        assertNotNull(user);
        assertEquals("Hello World", user.getMessage());
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RestDslRouteBuilder();
    }
}
