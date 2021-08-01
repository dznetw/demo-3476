package com.example.demo.services;

import com.example.demo.components.TimeSlot;
import com.example.demo.components.TimeSlotCalculator;
import com.example.demo.model.Appointment;
import com.example.demo.model.CarWorkshop;
import com.example.demo.repositories.AppointmentRepository;
import com.example.demo.repositories.CarWorkshopRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class AppointmentProposalService {

    private final TimeSlotCalculator timeSlotCalculator;
    private final CarWorkshopRepository workshopRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentProposalService(TimeSlotCalculator timeSlotCalculator,
                                      CarWorkshopRepository workshopRepository,
                                      AppointmentRepository appointmentRepository) {
        this.timeSlotCalculator = timeSlotCalculator;
        this.workshopRepository = workshopRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getProposalsFor(String carWorkshopName, LocalDate date, String serviceCode) {

        return getProposalsFor(carWorkshopName, date, serviceCode, LocalTime.MIN, LocalTime.MAX);
    }

    public List<Appointment> getProposalsFor(String carWorkshopName, LocalDate date, String serviceCode,
                                             LocalTime from, LocalTime until) {
        // error handling can be improved with specialized exceptions
        final CarWorkshop workshop = workshopRepository.findFirstByName(carWorkshopName)
                .orElseThrow();
        final Duration appointmentDuration = workshop.getServiceList().stream()
                .filter(s -> Objects.equals(s.getCode(), serviceCode))
                .findFirst()
                .map(com.example.demo.model.Service::getDuration)
                .orElseThrow();
        final List<Appointment> existingAppointments = getExistingAppointments(workshop.getName(), date);

        if (existingAppointments.isEmpty()) {
            // fast exit
            // TODO: this needs improvement, because an appointment may currently exceed time boundaries
            return streamLocalDateTimes(date, workshop, from, until)
                    .map(localDateTime -> newAppointment(localDateTime, appointmentDuration))
                    .collect(Collectors.toList());
        }

        final List<LocalTime> blockedSlots = findBlockedSlots(existingAppointments);

        return streamLocalDateTimes(date, workshop, from, until)
                .map(localDateTime -> newAppointment(localDateTime, appointmentDuration))
                // TODO: this needs improvement, because a shorter appointment might be already scheduled between
                //       'start' and 'end'
                .filter(appointment -> !blockedSlots.contains(appointment.getStart())
                        && !blockedSlots.contains(appointment.getEnd()))
                .collect(Collectors.toList());
    }

    private List<Appointment> getExistingAppointments(String carWorkshopName, LocalDate date) {
        final List<Appointment> existingAppointments = appointmentRepository.findByWorkshopAndDate(carWorkshopName, date);

        // idea adapted from "Optimal Job Scheduling" problem:
        // usual recommendation is to sort from long to short intervals if trying to
        // prune-out options as early as possible
        existingAppointments.sort(AppointmentProposalService::sortByDurationLongToShort);
        return existingAppointments;
    }

    private List<LocalTime> findBlockedSlots(List<Appointment> existingAppointments) {
        final Map<String, TimeSlot> timeSlots = new HashMap<>();

        for (Appointment appointment : existingAppointments) {
            timeSlotCalculator.getTimeSlotsCoveredBy(appointment)
                    .forEach(coveredSlot -> Optional.ofNullable(timeSlots.get(coveredSlot.getKey()))
                            .ifPresentOrElse(
                                    TimeSlot::incrementParallelCount,
                                    () -> timeSlots.put(coveredSlot.getKey(), coveredSlot)
                            )
                    );
        }

        return timeSlots.values().stream()
                .filter(TimeSlot::isBlocked)
                .sorted((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getKey(), o2.getKey()))
                .map(TimeSlot::getSlotTime)
                .collect(Collectors.toList());
    }

    private static int sortByDurationLongToShort(Appointment appointment1, Appointment appointment2) {
        final Duration duration1 = calculateDurationOf(appointment1);
        final Duration duration2 = calculateDurationOf(appointment2);

        return duration1.compareTo(duration2);
    }

    private static Duration calculateDurationOf(Appointment appointment) {
        final LocalTime start = appointment.getStart();
        final LocalTime end = appointment.getEnd();

        return Duration.between(start, end);
    }

    private static Stream<LocalDateTime> streamLocalDateTimes(LocalDate date, CarWorkshop workshop,
                                                              LocalTime from, LocalTime until) {
        final AtomicLong pointer = new AtomicLong(0);
        final Duration stepSize = workshop.getTimeSlotSize();

        return Stream.generate(() -> stepThroughLocalDateTime(pointer, date, stepSize, from, until))
                .limit(Duration.ofHours(24).dividedBy(stepSize))
                .filter(Objects::nonNull);
    }

    private static LocalDateTime stepThroughLocalDateTime(AtomicLong pointer, LocalDate localDate, Duration stepSize,
                                                          LocalTime from, LocalTime until) {
        final LocalDateTime next = LocalDateTime.of(localDate, LocalTime.MIDNIGHT)
                .plus(stepSize.multipliedBy(pointer.getAndIncrement()));
        final LocalTime nextLocalTime = next.toLocalTime();

        if (localDate.isEqual(next.toLocalDate())
                && !nextLocalTime.isBefore(from)
                && !nextLocalTime.isAfter(until)
        ) {
            return next;
        }

        return null;
    }

    private static Appointment newAppointment(LocalDateTime localDateTime, Duration appointmentDuration) {
        final Appointment appointment = new Appointment();
        appointment.setDate(localDateTime.toLocalDate());
        appointment.setStart(localDateTime.toLocalTime());
        appointment.setEnd(appointment.getStart().plus(appointmentDuration));
        // TODO: check whether setting 'workshop', here, is already needed

        return appointment;
    }
}
