package com.app.bean;

import com.app.Output;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HelloBean {

    public Output sayHello(String s) {
//        return "Hello " + s;
        return new Output( "Hello " + s, LocalDateTime.now());
    }

}
