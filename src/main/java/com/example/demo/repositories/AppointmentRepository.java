package com.example.demo.repositories;

import com.example.demo.model.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

// note: returning Streams might have better performance
@Component
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    @Query("SELECT a from Appointment a where a.workshop.name=:workshopName and a.date=:date")
    List<Appointment> findByWorkshopAndDate(String workshopName, LocalDate date);

    @Query("SELECT a from Appointment a where a.id=:id and a.workshop.name=:workshopName")
    Optional<Appointment> findAppointmentByIdAndWorkshop(Long id, String workshopName);

    @Query("SELECT a from Appointment a where a.workshop.name=:workshopName and a.date=:date and a.start>=:from and a.end<=:until")
    List<Appointment> findByWorkshopAndDateRange(String workshopName, LocalDate date, LocalTime from, LocalTime until);

}
