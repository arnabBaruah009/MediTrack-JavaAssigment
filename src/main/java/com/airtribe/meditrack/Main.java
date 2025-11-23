package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.Gender;
import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Person;
import com.airtribe.meditrack.service.DoctorService;

import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args){
        System.out.println("Starting project");
        Doctor doctor1 = new Doctor("Dr. John Doe", "jd@x.com", 1234567890L, "123 Main St", LocalDate.of(1980, 5, 15), Gender.MALE, "DOC-001", Specialization.CARDIOLOGIST);
        Doctor doctor2 = new Doctor("Dr. Jane Smith", "js@x.com", 9876543210L, "456 Elm St", LocalDate.of(1975, 8, 22), Gender.MALE, "DOC-002", Specialization.ENT_SPECIALIST);

        DoctorService doctorService = new DoctorService();
        doctorService.addDoctor(doctor1);
        doctorService.addDoctor(doctor2);

        System.out.println("All Doctors:");
        for(Doctor doc : doctorService.getAllDoctors()){
            System.out.println("Name: " + doc.getName() + ", Specialization: " + doc.getSpecialization());
        }
        Optional<Doctor> doctor = doctorService.getDoctorById("DOC-001");
        doctor.ifPresent(doc -> System.out.println("Doctor found with ID: " + doc.getName()));
    }
}
