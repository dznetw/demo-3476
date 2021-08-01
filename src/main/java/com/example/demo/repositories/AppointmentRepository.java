package com.example.demo.repositories;

import com.example.demo.model.Appointment;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface AppointmentRepository extends Repository<Appointment, Long> {

    // note: returning Stream might have better performance
    @Query("SELECT a from Appointments a where a.workshop.name=:workshopName and a.date=:date")
    List<Appointment> findByWorkshopAndDate(String workshopName, LocalDate date);
}
