package com.app;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RestDslRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /**
         * Use 'restlet', which is a very simple component for providing REST
         * services. Ensure that camel-restlet or camel-restlet-starter is
         * included as a Maven dependency first.
         */
        restConfiguration()
                .component("restlet")//todo: kto to robi pod spodem
                .host("localhost").port("8085")
                .bindingMode(RestBindingMode.json);

        /**
         * Configure the REST API (POST, GET, etc.)
         */
//        rest().path("/").consumes("application/json")
//                .get()
//                    .to("bean:helloBean");

        from("rest:get:hello?name={name}").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                Output output = new Output();
                output.setMessage("Hello " + exchange.getIn().getHeaders().get("name"));
                exchange.getIn().setBody(output);
            }
        }).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                Output body = exchange.getIn().getBody(Output.class);
                body.setTime(LocalDateTime.now());
                exchange.getOut().setBody(body);
            }
        })/*.bean(Output.class, "create(${header.name})")*/.marshal().json(JsonLibrary.Jackson);
//                .transform().simple("Hello ${header.name}");
    }
}
