package com.example.demo.repositories;

import com.example.demo.model.CarWorkshop;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface CarWorkshopRepository extends Repository<CarWorkshop, Long> {

    // note: returning Stream might have better performance
    @Query("SELECT c from CarWorkshop c where c.name=:workshopName")
    Optional<CarWorkshop> findFirstByName(String workshopName);
}
