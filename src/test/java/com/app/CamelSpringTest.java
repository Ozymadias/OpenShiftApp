package com.app;

import org.apache.camel.builder.RouteBuilder;
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
                interceptSendToEndpoint("http*").to("mock:http").skipSendToOriginalEndpoint();
            }
        });

        getMockEndpoint("mock:http").expectedMessageCount(1);

        template.sendBody("http://localhost:8085/hello?name=World", "Hello World");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RestDslRouteBuilder();
    }
}
