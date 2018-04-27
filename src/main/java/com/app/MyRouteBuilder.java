package com.app;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                .component("restlet")//todo: kto to robi pod spodem
                .host("0.0.0.0").port("8086")
                .bindingMode(RestBindingMode.json);

        from("rest:get:hello?name={name}")
                .process(exchange -> {exchange.getIn().setBody(new OldOutput());})
                .multicast()
                .parallelProcessing().to("direct:message").to("direct:time").end()
                .to("direct:intermediary");

        from("direct:intermediary").marshal().json(JsonLibrary.Jackson);

        from("direct:message").process(exchange -> {
            exchange.getIn().getBody(OldOutput.class).setMessage("Hello " + exchange.getIn().getHeaders().get("name"));
        });

        from("direct:time").process(exchange -> {
            exchange.getIn().getBody(OldOutput.class).setTime(LocalDateTime.now());
        });
    }
}
