package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.interfaces.Searchable;

import java.util.*;

public class DoctorService implements Searchable<Doctor> {
    private Map<String, Doctor> doctors;

    public DoctorService() {
        this.doctors = new HashMap<>();
    }

    //    Create
    public void addDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        if (doctors.containsKey(doctor.getDoctorId())) {
            throw new IllegalArgumentException("Doctor with ID " + doctor.getDoctorId() + " already exists");
        }
        doctors.put(doctor.getDoctorId(), doctor);
    }

    //    Read
    public Optional<Doctor> getDoctorByID(String doctorId) {
        return Optional.ofNullable(doctors.get(doctorId));
    }

    public Collection<Doctor> getAllDoctors() {
        return Collections.unmodifiableCollection(doctors.values());
    }

    //    Update
    public boolean updateDoctorName(String doctorId, String newName) {
        if (!doctors.containsKey(doctorId)) {
            return false;
        }
        Doctor doctor = doctors.get(doctorId);
        doctor.setName(newName);
        return true;
    }

    //    Delete
    public boolean deleteDoctor(String doctorId) {
        return doctors.remove(doctorId) != null;
    }

    //    Search
    @Override
    public List<Doctor> searchByKeyword(String keyword) {
        return doctors.values().stream()
                .filter(d -> d.getName().toLowerCase().contains(keyword.toLowerCase())
                        || d.getSpecialization().toString().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}
