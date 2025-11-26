package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.util.Validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {

    private final List<Appointment> appointments = new ArrayList<>();

    // ----------------------------
    // CREATE
    // ----------------------------
    public Appointment createAppointment(Long patientId, Long doctorId, LocalDateTime dateTime) {
        Validator.validateAppointment(patientId, doctorId, dateTime);
        ensureSlotFree(doctorId, dateTime, null);

        Appointment appointment =
                new Appointment(patientId, doctorId, dateTime, AppointmentStatus.PENDING);

        appointments.add(appointment);
        return appointment;
    }

    // ----------------------------
    // UPDATE TIME
    // ----------------------------
    public Appointment updateAppointmentTime(Appointment appointment, LocalDateTime newTime) {
        if (appointment == null)
            throw new IllegalArgumentException("Appointment cannot be null");

        Validator.validateAppointment(
                appointment.getPatientId(),
                appointment.getDoctorId(),
                newTime
        );

        ensureSlotFree(appointment.getDoctorId(), newTime, appointment);
        appointment.setAppointmentDate(newTime);
        return appointment;
    }

    // ----------------------------
    // TIME CONFLICT CHECK
    // ----------------------------
    private boolean isOverlapping(LocalDateTime t1, LocalDateTime t2) {
        long minutes = Math.abs(java.time.Duration.between(t1, t2).toMinutes());
        return minutes < 30; // 30-minute conflict window
    }

    private void ensureSlotFree(Long doctorId, LocalDateTime time, Appointment ignore) {
        boolean conflict = appointments.stream()
                .filter(a -> a != ignore)
                .filter(Appointment::isActive)
                .anyMatch(a -> a.getDoctorId().equals(doctorId)
                        && isOverlapping(a.getAppointmentDate(), time));

        if (conflict)
            throw new IllegalArgumentException("Doctor has another appointment within 30 minutes");
    }

    // ----------------------------
    // GETTERS
    // ----------------------------
    public List<Appointment> getAllAppointments() {
        return List.copyOf(appointments);
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointments.stream()
                .filter(a -> a.getStatus() == status)
                .toList();
    }

    public List<Appointment> getAppointmentByDoctor(Long doctorId) {
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(doctorId))
                .toList();
    }

    public List<Appointment> getAppointmentByPatient(Long patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .toList();
    }

    // ----------------------------
    // UPDATE STATUS
    // ----------------------------
    public void updateStatus(Appointment appointment, AppointmentStatus newStatus) {
        if (appointment == null)
            throw new IllegalArgumentException("Appointment cannot be null");

        // Block changing final status (Completed/Canceled)
        if (!appointment.isActive() && newStatus != appointment.getStatus()) {
            throw new IllegalArgumentException("Finalized appointments cannot change status");
        }

        appointment.setStatus(newStatus);
    }

    // ----------------------------
    // DELETE
    // ----------------------------
    public boolean deleteAppointment(Appointment appointment) {
        if (appointment == null)
            throw new IllegalArgumentException("Appointment cannot be null");

        return appointments.remove(appointment);
    }


}
