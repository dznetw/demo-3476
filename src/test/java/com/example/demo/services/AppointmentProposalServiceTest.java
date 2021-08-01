package com.example.demo.services;

import com.example.demo.components.TimeSlotCalculator;
import com.example.demo.model.Appointment;
import com.example.demo.model.CarWorkshop;
import com.example.demo.model.Service;
import com.example.demo.repositories.AppointmentRepository;
import com.example.demo.repositories.CarWorkshopRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

@ExtendWith(MockitoExtension.class)
class AppointmentProposalServiceTest {

    private static final List<LocalTime> expectedBlockedSlots = List.of(
            LocalTime.of(8, 0),
            LocalTime.of(8, 15),
            LocalTime.of(8, 30),
            LocalTime.of(8, 45),
            LocalTime.of(9, 0),
            LocalTime.of(9, 15),
            LocalTime.of(9, 30),
            LocalTime.of(9, 45),
            LocalTime.of(10, 0),
            LocalTime.of(10, 15),
            LocalTime.of(10, 30),
            LocalTime.of(10, 45)
    );
    private static final CarWorkshop WORKSHOP = getWorkshop();
    private static final List<Appointment> APPOINTMENT_LIST = newAppointments(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'"),
            List.of(
                    Pair.of("2019-01-01T08:00Z", "FIX"),
                    Pair.of("2019-01-01T08:00Z", "FIX"),
                    Pair.of("2019-01-01T08:00Z", "FIX")
            ),
            WORKSHOP
    );

    @Spy
    private TimeSlotCalculator calculatorSpy = new TimeSlotCalculator();
    @Mock
    private CarWorkshopRepository workshopRepositoryMock;
    @Mock
    private AppointmentRepository appointmentRepositoryMock;
    private AppointmentProposalService service;

    @BeforeEach
    void setUp() {
        Mockito.lenient()
                .doReturn(Optional.of(WORKSHOP))
                .when(workshopRepositoryMock).findFirstByName(anyString());
        Mockito.lenient()
                .doAnswer(invocation -> {
                    final LocalDate date = invocation.getArgument(1, LocalDate.class);
                    return APPOINTMENT_LIST.stream()
                            .filter(appointment -> appointment.getDate().isEqual(date))
                            .collect(Collectors.toList());
                })
                .when(appointmentRepositoryMock).findByWorkshopAndDate(anyString(), any(LocalDate.class));

        service = new AppointmentProposalService(calculatorSpy, workshopRepositoryMock, appointmentRepositoryMock);
    }

    @Test
    @DisplayName("when getProposalsFor")
    void getProposalsFor() throws Exception {
        final LocalDate date = DateTimeFormatter.ISO_DATE.parse("2019-01-01", LocalDate::from);
        final List<Appointment> workshopProposals = service.getProposalsFor(
                WORKSHOP.getName(),
                date,
                "INS");

        for (Appointment proposal : workshopProposals) {
            assertEquals(proposal.getDate(), date);
            assertFalse(expectedBlockedSlots.contains(proposal.getStart()),
                    "start time is not allowed to be a blocked slot!");
            assertFalse(expectedBlockedSlots.contains(proposal.getEnd()),
                    "end time is not allowed to be a blocked slot!");
        }
    }

    private static List<Appointment> newAppointments(DateTimeFormatter formatter, List<Pair<String, String>> timestampCodePairList, CarWorkshop carWorkshop) {
        final Map<String, Duration> serviceDurationMap = carWorkshop.getServiceList().stream()
                .collect(Collectors.toMap(Service::getCode, Service::getDuration));

        return timestampCodePairList.stream()
            .map(pair -> {

                try {
                    final LocalDateTime parsed = formatter.parse(pair.getFirst(), LocalDateTime::from);
                    final Duration duration = Optional.ofNullable(serviceDurationMap.get(pair.getSecond()))
                            .orElseThrow();
                    return Pair.of(parsed, duration);
                } catch(DateTimeParseException | NoSuchElementException e) {
                    // ignoring test data errors during setup for now
                    return null;
                }

            })
            .filter(Objects::nonNull)
            .map(pair -> {
                final LocalDateTime dateTime = pair.getFirst();
                final Duration duration = pair.getSecond();
                final Appointment appointment = new Appointment();

                appointment.setDate(dateTime.toLocalDate());
                appointment.setStart(dateTime.toLocalTime());
                appointment.setEnd(appointment.getStart().plus(duration));
                appointment.setWorkshop(carWorkshop);

                return appointment;
            })
            .collect(Collectors.toList());
    }

    private static CarWorkshop getWorkshop() {
        final CarWorkshop workshop = new CarWorkshop();
        workshop.setName("workshopB");
        workshop.setTimeSlotSize(Duration.of(15, ChronoUnit.MINUTES));
        workshop.setMaxParallelSlots(3);
        workshop.setServiceList(List.of(
                newService("OIL", Duration.of(10, ChronoUnit.MINUTES)),
                newService("FIX", Duration.of(3, ChronoUnit.HOURS)),
                newService("INS", Duration.of(1, ChronoUnit.HOURS))
        ));
        return workshop;
    }

    private static Service newService(String code, Duration duration) {
        final Service service = new Service();
        service.setCode(code);
        service.setDuration(duration);
        return service;
    }
}