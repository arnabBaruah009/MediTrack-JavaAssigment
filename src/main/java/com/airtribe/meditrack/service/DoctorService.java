package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorService {
    private List<Doctor> doctors;

    public DoctorService(){
        this.doctors = new ArrayList<>();
    }

//    Create

    public void addDoctor(Doctor doctor) {
        if(doctor == null){
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        doctors.add(doctor);
        System.out.println("Doctor added: " + doctor.getName());
    }

//    Read

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }

    public Optional<Doctor> getDoctorByName(String name) {
        return doctors.stream()
                .filter(doctor -> doctor.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Doctor> getDoctorById(String doctorId){
        return doctors.stream()
                .filter(doctor -> doctor.getDoctorId().equals(doctorId))
                .findFirst();
    }

//    Update

    public boolean updateDoctorName(String doctorId, String newName){
        Optional<Doctor> doctor = getDoctorById(doctorId);
        if(doctor.isPresent()){
            doctor.get().setName(newName);
            System.out.println("Doctor name updated to: " + newName);
            return true;
        }
        System.out.println("Doctor not found with ID: " + doctorId);
        return false;
    }

//    Delete

    public boolean deleteDoctor(String doctorId) {
        return doctors.removeIf(doctor -> doctor.getDoctorId().equals(doctorId));
    }
}
