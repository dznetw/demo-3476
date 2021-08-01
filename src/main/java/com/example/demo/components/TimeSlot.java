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
    // if multi-threading is required, use AtomicInteger instead
    private int parallelCount;

    private TimeSlot(String key, LocalTime slotTime, int maxParallelAppointments) {
        this.key = key;
        this.slotTime = slotTime;
        this.maxParallelAppointments = maxParallelAppointments;
    }

    public static TimeSlot newInstance(LocalTime slotTime, int maxParallelAppointments) {
        Objects.requireNonNull(slotTime, "Time Slot needs a local time instance!");

        return new TimeSlot(slotTime.format(KEY_FORMATTER), slotTime, maxParallelAppointments);
    }

    public String getKey() {
        return key;
    }

    public LocalTime getSlotTime() {
        return slotTime;
    }

    public void incrementParallelCount() {
        this.parallelCount += 1;
    }

    public boolean isBlocked() {
        return this.parallelCount >= maxParallelAppointments;
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
                .add("parallelAppointments=" + parallelCount)
                .toString();
    }
}
