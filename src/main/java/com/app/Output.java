package com.app;

import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public class Output {
    private String message;
    private LocalDateTime time;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Supplier<LocalDateTime> timeSupplier = LocalDateTime::now;

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
//        this.time = time;
        this.time = timeSupplier.get();
    }

    LocalDateTime getTimeAsLocalDateTime() {
        return time;
    }

    void setTimeSupplier(Supplier<LocalDateTime> timeSupplier) {
        this.timeSupplier = timeSupplier;
    }

    @Override
    public String toString() {
        return "Output{" +
                "message='" + message + '\'' +
                ", time=" + time +
                ", formatter=" + formatter +
                ", timeSupplier=" + timeSupplier +
                '}';
    }
}
