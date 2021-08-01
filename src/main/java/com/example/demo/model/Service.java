package com.example.demo.model;

import java.time.Duration;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Service {

    @Id
    private long id;
    private String code;
    private Duration duration;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
