package com.app;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.aggregate.AggregationStrategy;
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

//        from("rest:get:hello?name={name}").process(exchange -> {
//            Output output = new Output();
//            output.setMessage("Hello " + exchange.getIn().getHeaders().get("name"));
//            exchange.getIn().setBody(output);//out w jednym in'em w drugim; jak nie ma out'a to in z poprzedniego robi za out'a
//        }).process(exchange -> {
//            Output body = exchange.getIn().getBody(Output.class);
//            body.setTime(LocalDateTime.now());
//            exchange.getOut().setBody(body);
//        }).marshal().json(JsonLibrary.Jackson);

        from("rest:get:hello?name={name}").multicast().aggregationStrategy(new AggregationStrategy() {
            @Override
            public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                System.out.println("aggregate");
                if (oldExchange == null)
                    return newExchange;
                Output oldOutput = oldExchange.getIn().getBody(Output.class);
                Output newOutput = newExchange.getIn().getBody(Output.class);
                if (newOutput.getMessage() == null) {
                    oldOutput.setTime(newOutput.getTimeAsLocalDateTime());
                    System.out.println("1: " + newOutput.getTime());
                }
                else {
                    oldOutput.setMessage(newOutput.getMessage());
                    System.out.println("2: " + newOutput.getMessage());
                }
                return oldExchange;
            }
        }).parallelProcessing().enrich("direct:x").enrich("direct:y").end().marshal().json(JsonLibrary.Jackson);//.to("rest:get:hello?name={name}");

//        from("direct:serviceAggregator")
//                .multicast(new GroupedExchangeAggregationStrategy()).parallelProcessing()
//                .enrich("http://servicea.com").enrich("http://serviceb.com")
//                .end();

        from("direct:x").process(exchange -> {
            System.out.println("direct x");
            Output output = new Output();
            output.setMessage("Hello " + exchange.getIn().getHeaders().get("name"));
            exchange.getOut().setBody(output);
        });//.setBody();//.to("rest:get:hello?name={name}");//.to("direct:aggregator");

        from("direct:y").process(exchange -> {
            System.out.println("direct y");
            Output output = new Output();
            output.setTime(LocalDateTime.now());
            exchange.getOut().setBody(output);
        });//.setBody();//.to("rest:get:hello?name={name}");//.to("direct:aggregator");

//        from("direct:aggregator").aggregate(new AggregationStrategy() {
//            @Override
//            public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
//                if (oldExchange == null) {
//                    return newExchange;
//                }
//                Output oldOutput = oldExchange.getIn().getBody(Output.class);
//                Output newOutput = newExchange.getIn().getBody(Output.class);
//                if (newOutput.getMessage() != null)
//                    oldOutput.setTime(newOutput.getTimeAsLocalDateTime());
//                else
//                    oldOutput.setMessage(newOutput.getMessage());
//                return oldExchange;
//            }
//        }).body().completionTimeout(1000).marshal().json(JsonLibrary.Jackson).to("rest:get:hello?name={name}");


    }
}
