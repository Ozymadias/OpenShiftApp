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
//                interceptSendToEndpoint("http://localhost:8085/hello*").to("mock:http").skipSendToOriginalEndpoint();
                interceptSendToEndpoint("direct:sth").to("mock:http").skipSendToOriginalEndpoint();
            }
        });

        MockEndpoint mockEndpoint = getMockEndpoint("mock:http");
        mockEndpoint.expectedMessageCount(1);

//        template.sendBody("http://localhost:8085/hello?name=World", "Hello Worl");
//        mockEndpoint.expectedBodiesReceived("");
        Object response = template.requestBody("http://localhost:8085/hello?name=World", "S");
//        Object response = template.request("http://localhost:8085/hello?name=World", Object::notify);

        assertMockEndpointsSatisfied();
        Output user = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Output.class);
        System.out.println(user);
        assertNotNull(user);
        assertEquals("Hello World", user.getMessage());

//        template.sendBodyAndHeader(expectedBody, "foo", "bar");
//        mockEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RestDslRouteBuilder();
    }

    @Test
    public void httpGet() throws Exception {

        Exchange exchange = template.request("http://localhost:8085/hello?name=World", new Processor() {
            public void process(Exchange exchange) throws Exception {
            }
        });

//        assertExchange(exchange);
    }

    @Produce(uri = "http://localhost:8085/hello?name=World")
    protected ProducerTemplate testProducer;

    @Test
    public void getTest() throws Exception {

        RouteDefinition route = context.getRouteDefinitions().get(0);
        route.adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("http://localhost:8085/hello*").to("mock:http").skipSendToOriginalEndpoint();
            }
        });

        MockEndpoint mockEndpoint = getMockEndpoint("mock:http");
        mockEndpoint.expectedMessageCount(1);

//        template.sendBody("http://localhost:8085/hello?name=World", "Hello Worl");
        mockEndpoint.expectedBodiesReceived("");
//        Object response = template.requestBody("http://localhost:8085/hello?name=World", "S");
//        Object response = template.request("http://localhost:8085/hello?name=World", Object::notify);

        assertMockEndpointsSatisfied();
//        Object user = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody();
//        assertNull(user);
//        assertEquals("Hello A", user.getMessage());

//        template.sendBodyAndHeader(expectedBody, "foo", "bar");
        mockEndpoint.assertIsSatisfied();

    }
}
