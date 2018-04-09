package com.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HelloController {
    @RequestMapping("/")
    public Output getHello() {
        return new Output("Hello Adamm", LocalDateTime.now() );
    }
}
