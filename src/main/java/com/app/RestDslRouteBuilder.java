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

        from("rest:get:hello?name={name}").process(exchange -> {
            Output output = new Output();
            output.setMessage("Hello " + exchange.getIn().getHeaders().get("name"));
            exchange.getIn().setBody(output);
        }).process(exchange -> {
            Output body = exchange.getIn().getBody(Output.class);
            body.setTime(LocalDateTime.now());
            exchange.getOut().setBody(body);
        }).marshal().json(JsonLibrary.Jackson);
    }
}
