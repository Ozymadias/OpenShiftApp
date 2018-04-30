package com.app;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                .component("servlet")
                .host("0.0.0.0").port("8085")
                .bindingMode(RestBindingMode.json);

        rest().produces("application/json").get("/hello").route()
                .to("direct:sth");

        from("direct:sth")
                .process(exchange -> {exchange.getIn().setBody(new Output());})
                .multicast().id("multicast")
                .parallelProcessing().to("direct:message").to("direct:time").end();

        from("direct:message").process(exchange -> {
            exchange.getIn().getBody(Output.class).setMessage("Hello " + exchange.getIn().getHeaders().get("name"));
        });

        from("direct:time").process(exchange -> {
            exchange.getIn().getBody(Output.class).setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        });

        from("cxf:bean:cxfSoapServiceEndpoint")
                .process(exchange -> {exchange.getIn().setHeader("name", exchange.getIn().getBody(Input.class).getName());}).to("direct:sth");
    }
}
