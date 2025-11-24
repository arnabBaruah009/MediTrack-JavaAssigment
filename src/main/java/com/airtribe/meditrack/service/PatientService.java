package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.interfaces.Searchable;

import java.util.*;

public class PatientService implements Searchable<Patient> {
    private Map<String, Patient> patients;

    public PatientService() {
        // Initialize the patients map
        this.patients = new HashMap<>();
    }

    //    Create
    public void addPatient(Patient patient) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }
        if (patients.containsKey(patient.getPatientId())) {
            throw new IllegalArgumentException("Patient with ID " + patient.getPatientId() + " already exists");
        }
        patients.put(patient.getPatientId(), patient);
    }

    //    Read
    public Optional<Patient> getPatientByID(String patientId) {
        return Optional.ofNullable(patients.get(patientId));
    }

    public Collection<Patient> getPatients() {
        return Collections.unmodifiableCollection(patients.values());
    }

    //    Update
    public void updatePatientName(String patientId, String newName) {
        if (!patients.containsKey(patientId)) {
            return;
        }
        Patient patient = patients.get(patientId);
        patient.setName(newName);
    }

    //    Delete
    public boolean deletePatient(String patientId) {
        return patients.remove(patientId) != null;
    }


    @Override
    public List<Patient> searchByKeyword(String keyword) {
        return patients.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase())).toList();
    }
}
