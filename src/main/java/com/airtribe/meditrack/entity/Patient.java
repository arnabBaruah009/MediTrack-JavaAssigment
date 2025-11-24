package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Gender;

import java.time.LocalDate;

public class Patient extends Person {
    private String patientId;

    public Patient(String name, String email, Long phoneNumber, String address, LocalDate dob, Gender gender, String patientId) {
        super(name, email, phoneNumber, address, dob, gender);
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public void displayRole() {
        System.out.println("I am a Patient");
    }
}
