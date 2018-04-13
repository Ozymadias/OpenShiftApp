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

        from("rest:get:hello?name={name}").multicast().aggregationStrategy((oldExchange, newExchange) -> {
            if (oldExchange == null)
                return newExchange;
            Output oldOutput = oldExchange.getIn().getBody(Output.class);
            Output newOutput = newExchange.getIn().getBody(Output.class);
            if (newOutput.getMessage() == null)
                oldOutput.setTime(newOutput.getTimeAsLocalDateTime());
            else
                oldOutput.setMessage(newOutput.getMessage());
            return oldExchange;
        }).parallelProcessing().enrich("direct:message").enrich("direct:time").end().marshal().json(JsonLibrary.Jackson);

        from("direct:message").process(exchange -> {
            Output output = new Output();
            output.setMessage("Hello " + exchange.getIn().getHeaders().get("name"));
            exchange.getOut().setBody(output);
        });

        from("direct:time").process(exchange -> {
            Output output = new Output();
            output.setTime(LocalDateTime.now());
            exchange.getOut().setBody(output);
        });
    }
}
