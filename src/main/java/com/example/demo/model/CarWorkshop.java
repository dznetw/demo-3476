package com.example.demo.model;

import java.time.Duration;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CarWorkshops")
public class CarWorkshop {

    @Id
    private Long id;
    private String name;
    private Duration timeSlotSize;
    private int maxParallelSlots;
    @OneToMany
    private List<Service> serviceList;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getTimeSlotSize() {
        return timeSlotSize;
    }

    public void setTimeSlotSize(Duration timeSlotSize) {
        this.timeSlotSize = timeSlotSize;
    }

    public int getMaxParallelSlots() {
        return maxParallelSlots;
    }

    public void setMaxParallelSlots(int maxParallelSlots) {
        this.maxParallelSlots = maxParallelSlots;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
