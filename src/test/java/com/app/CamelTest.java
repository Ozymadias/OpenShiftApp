package com.app;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.jndi.JndiContext;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CamelTest{// extends CamelTestSupport {
//    @Override
//    protected RouteBuilder createRouteBuilder() {
//        return new RestDslRouteBuilder();
//    }

//    @Override
//    protected Context createJndiContext() throws Exception {
//        JndiContext context = new JndiContext();
//        context.bind("customTransformation", new CustomTransformation());
//        return context;
//    }


    @Before
    public void mockEndpoints() throws Exception {
        AdviceWithRouteBuilder mock = new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                // mock the for testing
                interceptSendToEndpoint("rest:get:hello?name=A")
                        .skipSendToOriginalEndpoint()
                        .to("mock:catchOutput");
            }
        };
//        context.getRouteDefinition("myRoute").adviceWith(context, mock);
    }

//    @Override
//    public boolean isUseAdviceWith() {
//        return true;
//    }

//    @Test
//    public void testWithRealFile() throws Exception {
//        MockEndpoint mockSolr = getMockEndpoint("mock:catchOutput");
//
//        context.start();
////        mockSolr.expectedMessageCount(1);
////        mockSolr.assertIsSatisfied();
//        Output user = mockSolr.getReceivedExchanges().get(0).getIn().getBody(Output.class);
//        assertNotNull(user);
//        assertEquals("Hello A", user.getMessage());
////        assertEquals("Donald Duck", user.getTime());
//        context.stop();
//    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void sayHelloTest() {
        // Call the REST API
        ResponseEntity<String> response = restTemplate.getForEntity("rest:get:hello?name=A", String.class);
        String s = response.getBody();
        assertEquals("Hello A", s);
    }
}
