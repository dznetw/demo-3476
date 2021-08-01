package com.example.demo.repositories;

import com.example.demo.model.Appointment;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface AppointmentRepository extends Repository<Appointment, Long> {

    @Query("SELECT a from Appointment a where a.workshop.name=:workshopName ")
    List<Appointment> findByWorkshopAndDate(String workshopName, LocalDate date);
}
