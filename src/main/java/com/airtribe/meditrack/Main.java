package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.Gender;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.service.PatientService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting project");
//        Doctor doctor1 = new Doctor("Dr. John Doe", "jd@x.com", 1234567890L, "123 Main St", LocalDate.of(1980, 5, 15), Gender.MALE, "DOC-001", Specialization.CARDIOLOGIST);
//        Doctor doctor2 = new Doctor("Dr. Jane Smith", "js@x.com", 9876543210L, "456 Elm St", LocalDate.of(1975, 8, 22), Gender.MALE, "DOC-002", Specialization.ENT_SPECIALIST);
//
//        DoctorServiceWithSearchable doctorService = new DoctorServiceWithSearchable();
//        doctorService.addDoctor(doctor1);
//        doctorService.addDoctor(doctor2);
//
//        System.out.println("All Doctors:");
//        for (Doctor doc : doctorService.getAllDoctors()) {
//            System.out.println(doc.getName());
//        }
//        doctorService.searchByKeyword("j").forEach(d -> System.out.println("Found Doctor: " + d.getName()));

        Patient patient1 = new Patient("Yonita Brown", "y1@x.com", 5551234567L, "789 Pine St", LocalDate.of(1990, 3, 10), Gender.FEMALE, "PAT-001");
        Patient patient2 = new Patient("Michael Green", "m1@x.com", 5559876543L, "321 Oak St", LocalDate.of(1985, 7, 25), Gender.MALE, "PAT-002");
        PatientService patientService = new PatientService();

        patientService.addPatient(patient1);
        patientService.addPatient(patient2);

        System.out.println("Searching for Patient by id " + patientService.getPatientByID("PAT-001").get().getName());
        patientService.searchByKeyword("bro").forEach(p -> System.out.println("Found Patient: " + p.getName()));
        patientService.getPatients().forEach(p -> System.out.println("Found Patient: " + p.getName()));
        patientService.updatePatientName("PAT-002", "Michael Blue");
        System.out.println("After update, Patient 2 name: " + patientService.getPatientByID("PAT-002").get().getName());
        patientService.getPatients().forEach(p -> System.out.println("Found Patient: " + p.getName()));
        patientService.deletePatient("PAT-001");
        System.out.println("After deletion of Patient 1:");
        patientService.getPatients().forEach(p -> System.out.println("Found Patient: " + p.getName()));
    }
}
