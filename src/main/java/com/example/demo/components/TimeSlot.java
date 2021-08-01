package com.example.demo.components;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;

public final class TimeSlot {

    // using root locale explicitly, might be adapted or provided by external config
    private static final DateTimeFormatter KEY_FORMATTER = DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT);
    private final String key;
    private final LocalTime slotTime;
    private final int maxParallelAppointments;
    private int parallelAppointments;

    public TimeSlot(LocalTime slotTime, int maxParallelAppointments) {
        this.slotTime = Objects.requireNonNull(slotTime, "Checked Slot needs a slot time!");
        this.key = this.slotTime.format(KEY_FORMATTER);
        this.maxParallelAppointments = maxParallelAppointments;
    }

    public String getKey() {
        return key;
    }

    public LocalTime getSlotTime() {
        return slotTime;
    }

    public void incrementParallelCount() {
        this.parallelAppointments += 1;
    }

    public boolean isBlocked() {
        return this.parallelAppointments >= maxParallelAppointments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeSlot that = (TimeSlot) o;
        return slotTime.equals(that.slotTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotTime);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TimeSlot.class.getSimpleName() + "[", "]")
                .add("key='" + key + "'")
                .add("slotTime=" + slotTime)
                .add("maxParallelAppointments=" + maxParallelAppointments)
                .add("parallelAppointments=" + parallelAppointments)
                .toString();
    }
}
