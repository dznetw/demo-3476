package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Appointments")
public class Appointment {

    @Id
    private Long id;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    @OneToOne
    private CarWorkshop workshop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public CarWorkshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(CarWorkshop workshop) {
        this.workshop = workshop;
    }
}
