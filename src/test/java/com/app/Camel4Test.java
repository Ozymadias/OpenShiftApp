package com.app;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.restlet.DefaultRestletBinding;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @version
 */
public class Camel4Test extends CamelTestSupport {

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndi = super.createRegistry();
        jndi.bind("myBinding", new DefaultRestletBinding());
        return jndi;
    }

    @Test
    public void testRestletProducerGet() throws Exception {
        String out = template.requestBody("http://localhost:8085/hello", null, String.class);
        assertEquals("123;Donald Duck", out);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // configure to use restlet on localhost with the given port
                restConfiguration().component("restlet").host("localhost").port(8085).endpointProperty("restletBinding", "#myBinding");

                // use the rest DSL to define the rest services
//                rest("/hello")
//                        .get("{id}/basic")
//                        .route()
                from("rest:get:hello?name=A")
                        .to("mock:input")
                        .process(new Processor( ) {
                            public void process(Exchange exchange) throws Exception {
                                String id = exchange.getIn().getHeader("id", String.class);
                                exchange.getOut().setBody(id + ";Donald Duck");
                            }
                        });
            }
        };
    }
}
