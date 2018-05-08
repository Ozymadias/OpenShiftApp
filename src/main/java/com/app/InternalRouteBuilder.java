package com.app;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class InternalRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:sth")
                .process(exchange -> {
                    exchange.getIn().setBody(new Output());
                })
                .multicast().id("multicast")
                .parallelProcessing().to("direct:message").to("direct:time").end();

        from("direct:message").process(exchange -> {
            exchange.getIn().getBody(Output.class).setMessage("Hello " + exchange.getIn().getHeaders().get("name"));
        });

        from("direct:time").process(exchange -> {
            exchange.getIn().getBody(Output.class).setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        });
    }
}
