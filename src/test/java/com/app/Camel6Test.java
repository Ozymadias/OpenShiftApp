package com.app;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class Camel6Test extends CamelTestSupport {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
//        return new RouteBuilder() {
//
////            restConfiguration()
////                .component("restlet")//todo: kto to robi pod spodem
////                .host("localhost").port("8085");
//            @Override
//            public void configure() throws Exception {
//                from("rest:get:hello?name=name").to("mock:test");
//            }
//        };
        return new RestDslRouteBuilder();
    }

//    @EndTest
    @Test
    public void Cameltest() throws Exception {
        MockEndpoint test = getMockEndpoint("mock:test");
        test.expectedBodiesReceived("Testing Endpoint");
        template.sendBody("rest:get:hello?name=name", "Testing Endpoint");
        test.assertIsSatisfied();
    }
}
