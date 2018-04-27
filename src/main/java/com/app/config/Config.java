package com.app.config;

import com.app.MyInterface;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.cxf.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Autowired
    private Bus bus;

    @Bean
    public CxfEndpoint cxfSoapServiceEndpoint() {
        CxfEndpoint cxfEndpoint = new CxfEndpoint();
        cxfEndpoint.setAddress("/soapAddress");
        cxfEndpoint.setServiceClass(MyInterface.class);
//        cxfEndpoint.setWsdlURL("wsdl/myWsdl.wsdl");
        cxfEndpoint.setBus(bus);
        return cxfEndpoint;
    }
}
