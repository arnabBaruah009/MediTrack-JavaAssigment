package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.util.AppointmentValidator;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {

    private final List<Appointment> appointments = new ArrayList<>();

    public Appointment createAppointment(Long patientId, Long doctorId, LocalDateTime dateTime) {
        AppointmentValidator.validateData(patientId, doctorId, dateTime);
        ensureSlotFree(doctorId, dateTime, null);

        Appointment appointment =
                new Appointment(patientId, doctorId, dateTime, AppointmentStatus.PENDING);

        appointments.add(appointment);
        return appointment;
    }

    public Appointment updateAppointmentTime(Appointment appointment, LocalDateTime newTime) {
        if (appointment == null)
            throw new IllegalArgumentException("Appointment cannot be null");

        AppointmentValidator.validateData(
                appointment.getPatientId(),
                appointment.getDoctorId(),
                newTime
        );

        ensureSlotFree(appointment.getDoctorId(), newTime, appointment);
        appointment.setAppointmentDate(newTime);
        return appointment;
    }

    private void ensureSlotFree(Long doctorId, LocalDateTime time, Appointment ignore) {
        if (doctorHasAppointment(doctorId, time, ignore))
            throw new IllegalArgumentException("Doctor already has an appointment at this time");
    }

    private boolean doctorHasAppointment(Long doctorId, LocalDateTime time, Appointment ignore) {
        return appointments.stream()
                .filter(a -> a != ignore)
                .anyMatch(a -> a.getDoctorId().equals(doctorId)
                        && a.getAppointmentDate().equals(time));
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
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

    public void updateStatus(Appointment appointment, AppointmentStatus newStatus) {
        if (appointment == null)
            throw new IllegalArgumentException("Appointment cannot be null");

        appointment.setStatus(newStatus);
    }

    public boolean deleteAppointment(Appointment appointment) {
        if (appointment == null)
            throw new IllegalArgumentException("Appointment cannot be null");

        return appointments.remove(appointment);
    }
}
