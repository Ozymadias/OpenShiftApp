package com.app;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class ExternalRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                .component("servlet")
                .host("0.0.0.0").port("8085")
                .bindingMode(RestBindingMode.json);

        rest().produces("application/json").get("/hello").route()
                .to("direct:sth");

        from("cxf:bean:cxfSoapServiceEndpoint")
                .process(exchange -> {exchange.getIn().setHeader("name", exchange.getIn().getBody(Input.class).getName());}).to("direct:sth");
    }
}
