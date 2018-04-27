package com.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OldOutput {
    private String message;
    private LocalDateTime time;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time.format(formatter);
    }

    void setTime(LocalDateTime time) {
        this.time = time;
    }

    LocalDateTime getTimeAsLocalDateTime() {
        return time;
    }
}
