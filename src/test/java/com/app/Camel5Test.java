package com.app;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.AvailablePortFinder;
import org.junit.Test;
import org.junit.runner.Request;
import org.skyscreamer.jsonassert.JSONAssert;

public class Camel5Test{ //extends CustomCamelContextTestSupport {
//        private int port;
//
//        @Override
//        protected RouteBuilder createRouteBuilder() throws Exception {
//            port = AvailablePortFinder.getNextAvailable(8080);
//            setConfigurationProperty("com.margic.pihex.api.port", Integer.toString(port));
//            setConfigurationProperty("com.margic.pihex.servo.conf", "${sys:user.dir}/target/test-classes/confwritetest/conf/");
//            return new ServoConfigRouteBuilder();
//        }
//        @Test
//        public void testServoConfigGet() throws Exception {
//            String content = Request.Get("http://localhost:" + port + "/servoconfig/0")
//                    .addHeader("Accept", "application/json")
//                    .execute()
//                    .returnContent()
//                    .asString();
//            log.info(content);
//            JSONAssert.assertEquals("{\"name\":\"Leg 0 Coxa\",\"channel\":0,\"range\":180,\"center\":0,\"lowLimit\":-90,\"highLimit\":90}", content, true);
//        }
//    }
}
