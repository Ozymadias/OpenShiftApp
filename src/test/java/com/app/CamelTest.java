package com.app;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.Is.is;

public class CamelTest extends CamelTestSupport {
//    @Override
//    public boolean isUseAdviceWith() {
//        // remember to override this method and return true to tell Camel that we are using advice-with in the routes
//        return true;
//    }

    @Test
    public void message() throws Exception {
//        RouteDefinition route = context.getRouteDefinitions().get(0);
//        route.adviceWith(context, new AdviceWithRouteBuilder() {
//            @Override
//            public void configure() throws Exception {
////                replaceFromWith("mock:message");
//                weaveAddLast().to("mock:message");
////                interceptSendToEndpoint("direct:intermediary").to("mock:result").skipSendToOriginalEndpoint();
//            }
//        });

        MockEndpoint mock = getMockEndpoint("mock:message");
        mock.expectedMessageCount(1);

        String name = "World";
        template.requestBodyAndHeader("direct:message", new Output(), "name", name);

        assertMockEndpointsSatisfied();

//        String message = mock.getReceivedExchanges().get(0).getIn().getBody(Output.class).getMessage();
//        assertNotNull(message);
//        assertEquals(message, "Hello " + name);
    }

    @Test
    public void time() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:time");
        mockEndpoint.expectedMessageCount(1);

        LocalDateTime before = LocalDateTime.now();
        template.requestBodyAndHeader("direct:time", new Output(), "name", "World");

        assertMockEndpointsSatisfied();

        Output output = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Output.class);
        assertNotNull(output);
        assertTrue(output.getTimeAsLocalDateTime().compareTo(before) > 0);
        assertTrue(output.getTimeAsLocalDateTime().compareTo(LocalDateTime.now()) < 0);
    }

    @Test
    public void integration() throws Exception {
        RouteDefinition route = context.getRouteDefinitions().get(0);
        route.adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("direct:intermediary").to("mock:result").skipSendToOriginalEndpoint();
            }
        });

        MockEndpoint mockEndpoint = getMockEndpoint("mock:result");
        mockEndpoint.expectedMessageCount(1);

        LocalDateTime before = LocalDateTime.now();

        String name = "World";
//        template.requestBody("rest:gWet:hello?name=" + name, "");
        template.requestBodyAndHeader("direct:start", "", "name", name);

        assertMockEndpointsSatisfied();
        Output output = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Output.class);

        assertNotNull(output);
        assertEquals("Hello " + name, output.getMessage());
        assertTrue(output.getTimeAsLocalDateTime().compareTo(before) > 0);
        assertTrue(output.getTimeAsLocalDateTime().compareTo(LocalDateTime.now()) < 0);
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RestDslRouteBuilder();
    }
}
