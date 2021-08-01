package com.example.demo.components;

import com.example.demo.model.Appointment;
import com.example.demo.model.CarWorkshop;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

class TimeSlotCalculatorTest {

    private static final DateTimeFormatter KEY_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT);
    private TimeSlotCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new TimeSlotCalculator();
    }

    @ParameterizedTest(name = "when determining covered timeslots,"
            + " all slots between start and end (exclusive) must be included, dependent on step size {2}: [{0}, {1}[ -> {3}")
    @ArgumentsSource(Params.class)
    void getTimeSlotsCoveredBy(LocalTime start, LocalTime end, Duration timeSlotSize,
                               List<String> expectedTimeSlotKeys) {
        final Appointment appointment = newtAppointment(start, end, timeSlotSize);

        final List<TimeSlot> result = calculator.getTimeSlotsCoveredBy(appointment);

        assertEquals(expectedTimeSlotKeys.size(), result.size());

        final List<String> resultKeys = result.stream()
                .map(TimeSlot::getKey)
                .collect(Collectors.toList());

        assertTrue(resultKeys.containsAll(expectedTimeSlotKeys),
                "Expecting result contain all time slot keys!");
    }

    private static Appointment newtAppointment(LocalTime start, LocalTime end, Duration timeSlotSize) {
        final Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.now());
        appointment.setStart(start);
        appointment.setEnd(end);
        appointment.setWorkshop(getWorkshop(timeSlotSize));
        return appointment;
    }

    private static CarWorkshop getWorkshop(Duration timeSlotSize) {
        final CarWorkshop workshop = new CarWorkshop();
        workshop.setName("workshop");
        workshop.setTimeSlotSize(timeSlotSize);

        return workshop;
    }

    public static final class Params implements ArgumentsProvider {

        private static final Duration DURATION_15_MINUTES = Duration.of(15, ChronoUnit.MINUTES);
        private static final Duration DURATION_10_MINUTES = Duration.of(10, ChronoUnit.MINUTES);

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            final Stream.Builder<Arguments> builder = Stream.builder();

            builder.add(newTestArguments(
                    LocalTime.of(1, 0),
                    Duration.of(11, ChronoUnit.HOURS),
                    DURATION_15_MINUTES
            ));

            builder.add(newTestArguments(
                    LocalTime.of(5, 0),
                    Duration.of(2, ChronoUnit.HOURS),
                    DURATION_10_MINUTES
            ));

            return builder.build();
        }

        private Arguments newTestArguments(LocalTime start, Duration appointmentDuration, Duration timeSlotSize) {
            final long dividedBy = appointmentDuration.dividedBy(timeSlotSize);
            final List<String> expectedSlots = new ArrayList<>((int) dividedBy);

            LocalTime current = start;
            for (int i = 0; i < dividedBy; i++) {
                expectedSlots.add(KEY_FORMATTER.format(current));
                current = current.plus(timeSlotSize);
            }

            return Arguments.of(start, start.plus(appointmentDuration), timeSlotSize, expectedSlots);
        }

    }
}