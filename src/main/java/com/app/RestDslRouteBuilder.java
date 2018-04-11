package com.app;

import com.app.bean.HelloBean;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

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
                .component("restlet")
                .host("localhost").port("8085")
                .bindingMode(RestBindingMode.json);

        /**
         * Configure the REST API (POST, GET, etc.)
         */
        rest().path("/").consumes("application/json")
                .get()
                    .to("bean:helloBean");

        from("rest:get:hello?name={name}").bean(HelloBean.class, "sayHello(${header.name})").marshal().json(JsonLibrary.Jackson);
//                .transform().simple("Hello ${header.name}");

//        from("direct:start").setHeader(Exchange.HTTP_METHOD).simple("name=${body}").
    }
}
