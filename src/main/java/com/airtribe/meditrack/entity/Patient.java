package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Gender;

import java.time.LocalDate;

public class Patient extends Person {
    private Long patientId;

    public Patient(String name, String email, Long phoneNumber, String address, LocalDate dob, Gender gender, Long patientId) {
        super(name, email, phoneNumber, address, dob, gender);
        this.patientId = patientId;
    }


    @Override
    public void displayRole() {
        System.out.println("I am a Patient");
    }
}
