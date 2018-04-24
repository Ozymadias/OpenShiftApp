package com.app;

import org.apache.camel.EndpointInject;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.DataFormatClause;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.Is.is;

public class CamelTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:afetLog")
    MockEndpoint afterLog;

    @Test
    public void integration() throws Exception {
        RouteDefinition route = context.getRouteDefinitions().get(0);
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
//                replaceFromWith("direct:start");
//                interceptSendToEndpoint("direct:intermediary").to("mock:http").skipSendToOriginalEndpoint();
                weaveAddLast().to("mock:http");
//                weaveById("loggerId").replace().to("mock:afetLog");
                weaveById("m").after().to("mock:beforeMarshal");
//                weaveByType(DataFormatClause.class).replace().to("mock:http");
            }
        });

        afterLog.whenAnyExchangeReceived(exchange -> LoggerFactory.getLogger("logger").debug("Not dummy"));


        MockEndpoint mockEndpoint = getMockEndpoint("mock:http");
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived("expected");
//mockEndpoint.expects();

        String before = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        template.requestBody("rest:get:hello?name=World", "");

//        template.asyncRequestBody().get();


        assertMockEndpointsSatisfied();


        Output output = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Output.class);

        assertNotNull(output);
        assertEquals("Hello World", output.getMessage());


        assertThat(output.getTime(), anyOf(is(before), is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))));
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new MyRouteBuilder();
    }
}
