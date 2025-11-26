package com.airtribe.meditrack.util;

import java.time.LocalDateTime;

public class AppointmentValidator {

    public static boolean isValidSlot(LocalDateTime dateTime) {
        int minute = dateTime.getMinute();
        return minute == 0 || minute == 30;
    }

    public static void validateData(Long patientId, Long doctorId, LocalDateTime dateTime) {

        if (patientId == null || patientId <= 0)
            throw new IllegalArgumentException("Patient ID is invalid");

        if (doctorId == null || doctorId <= 0)
            throw new IllegalArgumentException("Doctor ID is invalid");

        if (dateTime == null)
            throw new IllegalArgumentException("Appointment date cannot be null");

        if (dateTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Appointment date cannot be in the past");

        if (!isValidSlot(dateTime))
            throw new IllegalArgumentException("Appointments must be on the hour or half-hour");
    }
}
