package com.app;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Status;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Camel3Test extends CamelTestSupport {
//    @Test
//    public void testRestletProducer() throws Exception {
//
//        String out = template.requestBodyAndHeaders("direct:start", null, headers, String.class);
//        assertEquals("<response>Beer is Good</response>", out);
//    }
//
//
//    @Override
//    protected RouteBuilder createRouteBuilder() throws Exception {
//        return new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                from("direct:start").to("restlet:http://localhost:" + portNum + "/users/{id}/like/{beverage.beer}");
//
//                // START SNIPPET: e1
//                from("restlet:http://localhost:" + portNum + "/users/{id}/like/{beer}")
//                        .process(new Processor() {
//                            public void process(Exchange exchange) throws Exception {
//                                // the Restlet request should be available if needed
//                                Request request = exchange.getIn().getHeader(RestletConstants.RESTLET_REQUEST, Request.class);
//                                assertNotNull("Restlet Request", request);
//
//                                // use Restlet API to create the response
//                                Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
//                                assertNotNull("Restlet Response", response);
//                                response.setStatus(Status.SUCCESS_OK);
//                                response.setEntity("<response>Beer is Good</response>", MediaType.TEXT_XML);
//                                exchange.getOut().setBody(response);
//                            }
//                        });
//                // END SNIPPET: e1
//            }
//        };
//    }
}
