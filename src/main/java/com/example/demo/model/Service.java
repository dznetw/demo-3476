package com.example.demo.model;

import java.time.Duration;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Service {

    @Id
    private Long id;
    private String code;
    private Duration duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
