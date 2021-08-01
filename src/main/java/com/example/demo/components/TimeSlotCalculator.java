package com.example.demo.components;

import com.example.demo.model.Appointment;
import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotCalculator {

    public List<TimeSlot> getTimeSlotsCoveredBy(Appointment appointment) {
        final LocalTime end = appointment.getEnd();
        final int maxParallelSlots = appointment.getWorkshop().getMaxParallelSlots();
        final Duration timeSlotSize = appointment.getWorkshop().getTimeSlotSize();
        final List<TimeSlot> coveredTimeSlots = new LinkedList<>();

        LocalTime currentSlot = appointment.getStart();

        while (currentSlot.isBefore(end)) {
            final TimeSlot slot = new TimeSlot(currentSlot, maxParallelSlots);
            slot.incrementParallelCount();

            coveredTimeSlots.add(slot);
            currentSlot = currentSlot.plus(timeSlotSize);
        }

        return coveredTimeSlots;
    }
}
