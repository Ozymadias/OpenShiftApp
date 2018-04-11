package com.app;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Output {
    private String message;
    private LocalDateTime time;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time.format(formatter);
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

//    public Output(String message, LocalDateTime time) {
//        this.message = message;
//        this.time = time;
//    }

    public Output create(String name) {
        this.message = "Hello " + name;
        this.time = LocalDateTime.now();
        return this;
    }
}
