package com.app;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CamelTest extends CamelTestSupport {
    @Test
    public void integration() throws Exception {
        RouteDefinition route = context.getRouteDefinitions().get(0);
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveById("multicast").after().to("mock:http");
            }
        });

        MockEndpoint mockEndpoint = getMockEndpoint("mock:http");
        mockEndpoint.expectedMessageCount(1);

        String before = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        template.requestBodyAndHeader("direct:sth", new Output(), "name", "World");

        assertMockEndpointsSatisfied();
        Output output = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Output.class);
        assertNotNull(output);
        assertEquals("Hello World", output.getMessage());
        assertTrue(output.getTime().compareTo(before) >= 0);
        assertTrue(output.getTime().compareTo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) <= 0);
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new InternalRouteBuilder();
    }
}
