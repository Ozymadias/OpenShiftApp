package com.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public Output(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }
}
