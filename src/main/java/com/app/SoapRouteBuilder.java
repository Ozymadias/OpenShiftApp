package com.app;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SoapRouteBuilder extends RouteBuilder {
    public void configure() {

        from("cxf:bean:cxfSoapServiceEndpoint")
                .process(exchange -> {
                    Output output = new Output();
                    Input input = exchange.getIn().getBody(Input.class);

                    output.setMessage("Hello ".concat(input.getName()));
                    output.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    exchange.getOut().setBody(output);
                });
    }
}
