package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Gender;
import com.airtribe.meditrack.constants.Specialization;

import java.time.LocalDate;

public class Doctor extends Person{
    private String doctorId;
    private Specialization specialization;

    public Doctor(String name, String email, Long phoneNumber, String address, LocalDate dob, Gender gender, String doctorId, Specialization specialization) {
        super(name, email, phoneNumber, address, dob, gender);
        this.doctorId = doctorId;
        this.specialization = specialization;
    }

    // Constructor chaining
    public Doctor(String name, String email) {
        this(name, email, 565757L, "Ghy", LocalDate.now(), Gender.MALE, "Doc-001", Specialization.ONCOLOGIST);
    }

    @Override
    public void displayRole() {
        System.out.println("I am a Doctor specializing in " + specialization);
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
