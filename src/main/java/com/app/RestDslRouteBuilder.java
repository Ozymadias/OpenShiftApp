package com.app;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RestDslRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                .component("restlet")//todo: kto to robi pod spodem
                .host("localhost").port("8085")
                .bindingMode(RestBindingMode.json);

        rest("/hello?name={name}").get().to("direct:start");

        from("direct:start").process(exchange -> {exchange.getIn().setBody(new Output());})
                .multicast()
                .parallelProcessing().to("direct:message").to("direct:time").end()
                .to("mock:result").marshal().json(JsonLibrary.Jackson);

        from("direct:message").process(exchange -> {
            exchange.getIn().getBody(Output.class).setMessage("Hello " + exchange.getIn().getHeaders().get("name"));
        }).to("mock:message");

        from("direct:time").process(exchange -> {
            exchange.getIn().getBody(Output.class).setTime(LocalDateTime.now());
        }).to("mock:time");
    }
}
