package com.app;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
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
//    @EndpointInject(uri = "{{animalSource}}")
//    protected ProducerTemplate animalSource;

    @Test
    public void testHttpInterceptSendToEndpoint() throws Exception {
        RouteDefinition route = context.getRouteDefinitions().get(0);
        route.adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("direct:intermediary").to("mock:http").skipSendToOriginalEndpoint();
            }
        });

        MockEndpoint mockEndpoint = getMockEndpoint("mock:http");
        mockEndpoint.expectedMessageCount(1);

        String before = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        template.requestBodyAndHeader("rest:get:hello", "", "name", "World");
//        animalSource.requestBodyAndHeader("", "name", "World");

        assertMockEndpointsSatisfied();
        Output output = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Output.class);

        assertNotNull(output);
        assertEquals("Hello World", output.getMessage());
        assertThat(output.getTime(), anyOf(is(before), is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))));
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RestDslRouteBuilder();
    }
}
