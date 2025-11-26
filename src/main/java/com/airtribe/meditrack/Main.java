package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.constants.Gender;
import com.airtribe.meditrack.constants.PaymentStatus;
import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillingService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.billingstrategy.GSTBillingStrategy;
import com.airtribe.meditrack.util.billingstrategy.SeniorCitizenBillingStategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // Services
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final BillingService billingService = new BillingService();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            System.out.println("\n======== MediTrack Management System ========");
            System.out.println("1. Manage Appointments");
            System.out.println("2. Manage Doctors");
            System.out.println("3. Manage Patients");
            System.out.println("4. Manage Billing");
            System.out.println("0. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> appointmentMenu();
                case 2 -> doctorMenu();
                case 3 -> patientMenu();
                case 4 -> billingMenu();
                case 0 -> {
                    running = false;
                    System.out.println("Exiting MediTrack. Goodbye!");
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ---------------------------------------------------------
    // APPOINTMENTS MENU
    // ---------------------------------------------------------
    private static void appointmentMenu() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n====== Appointment Management ======");
            System.out.println("1. Create Appointment");
            System.out.println("2. Update Appointment Time");
            System.out.println("3. View All Appointments");
            System.out.println("4. View By Doctor ID");
            System.out.println("5. View By Patient ID");
            System.out.println("6. Update Appointment Status");
            System.out.println("7. Delete Appointment");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> createAppointment();
                    case 2 -> updateAppointmentTime();
                    case 3 -> showAppointments(appointmentService.getAllAppointments());
                    case 4 -> viewAppointmentsByDoctor();
                    case 5 -> viewAppointmentsByPatient();
                    case 6 -> updateAppointmentStatus();
                    case 7 -> deleteAppointment();
                    case 0 -> loop = false;
                    default -> System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void createAppointment() {
        Long patientId = readLong("Enter patient ID: ");
        Long doctorId = readLong("Enter doctor ID: ");
        LocalDateTime time = readDate("Enter appointment time (yyyy-MM-dd HH:mm): ");

        Appointment appointment = appointmentService.createAppointment(patientId, doctorId, time);
        System.out.println("Appointment created: ");
        appointmentService.viewAppointment(appointment);
    }

    private static void updateAppointmentTime() {
        Long patientId = readLong("Enter patient ID for appointment: ");

        List<Appointment> list = appointmentService.getAppointmentByPatient(patientId);

        if (list.isEmpty()) {
            System.out.println("No appointments found!");
            return;
        }

        showAppointments(list);

        int index = readInt("Select appointment index to update: ");
        Appointment appt = list.get(index);

        LocalDateTime newTime = readDate("Enter new time (yyyy-MM-dd HH:mm): ");

        appointmentService.updateAppointmentTime(appt, newTime);
        System.out.println("Appointment time updated.");
    }

    private static void updateAppointmentStatus() {
        Long patientId = readLong("Enter patient ID: ");
        List<Appointment> list = appointmentService.getAppointmentByPatient(patientId);

        if (list.isEmpty()) {
            System.out.println("No appointments found!");
            return;
        }

        showAppointments(list);

        int index = readInt("Select appointment index to update: ");
        Appointment appt = list.get(index);

        System.out.println("1. COMPLETED\n2. CANCELLED\n3. PENDING");
        int status = readInt("Choose new status: ");

        AppointmentStatus newStatus = switch (status) {
            case 1 -> AppointmentStatus.COMPLETED;
            case 2 -> AppointmentStatus.CANCELLED;
            case 3 -> AppointmentStatus.PENDING;
            default -> throw new IllegalArgumentException("Invalid status");
        };

        appointmentService.updateStatus(appt, newStatus);
        System.out.println("Status updated.");
    }

    private static void viewAppointmentsByDoctor() {
        Long doctorId = readLong("Enter doctor ID: ");
        showAppointments(appointmentService.getAppointmentByDoctor(doctorId));
    }

    private static void viewAppointmentsByPatient() {
        Long patientId = readLong("Enter patient ID: ");
        showAppointments(appointmentService.getAppointmentByPatient(patientId));
    }

    private static void deleteAppointment() {
        Long patientId = readLong("Enter patient ID: ");
        List<Appointment> list = appointmentService.getAppointmentByPatient(patientId);

        if (list.isEmpty()) {
            System.out.println("No appointments found!");
            return;
        }

        showAppointments(list);

        int index = readInt("Select appointment index to delete: ");
        boolean removed = appointmentService.deleteAppointment(list.get(index));

        System.out.println(removed ? "Deleted successfully!" : "Delete failed!");
    }

    private static void showAppointments(Collection<Appointment> appointments) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        for (Appointment a : appointments) {
            appointmentService.viewAppointment(a);
        }
    }

    // ---------------------------------------------------------
    // DOCTOR MENU
    // ---------------------------------------------------------
    private static void doctorMenu() {
        boolean loop = true;
        while (loop) {
            System.out.println("\n====== Doctor Management ======");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctor By ID");
            System.out.println("3. View All Doctors");
            System.out.println("4. Update Doctor Name");
            System.out.println("5. Delete Doctor");
            System.out.println("6. Search Doctors");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addDoctor();
                    case 2 -> viewDoctorById();
                    case 3 -> showDoctors(doctorService.getAllDoctors());
                    case 4 -> updateDoctorName();
                    case 5 -> deleteDoctor();
                    case 6 -> searchDoctor();
                    case 0 -> loop = false;
                    default -> System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void addDoctor() {
        String id = readString("Enter doctor ID: ");
        String name = readString("Enter name: ");
        String email = readString("Enter email: ");
        Long phone = readLong("Enter phone number: ");
        String address = readString("Enter address: ");

        LocalDate dob = readDateOnly("Enter DOB (yyyy-MM-dd): ");

        System.out.println("Select Gender: 1.MALE  2.FEMALE  3.OTHERS");
        int g = readInt("Enter: ");
        Gender gender = switch (g) {
            case 1 -> Gender.MALE;
            case 2 -> Gender.FEMALE;
            case 3 -> Gender.OTHERS;
            default -> throw new IllegalArgumentException("Invalid gender selection!");
        };

        // SPECIALIZATION SELECTION
        System.out.println("\nSelect Specialization:");
        Specialization[] values = Specialization.values();

        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }

        int choice = readInt("Enter specialization number: ");

        if (choice < 1 || choice > values.length) {
            throw new IllegalArgumentException("Invalid specialization selection!");
        }

        Specialization specialization = values[choice - 1];

        // Doctor constructor => Person fields first, then doctor fields
        Doctor doctor = new Doctor(
                name, email, phone, address, dob, gender,
                id, specialization
        );

        doctorService.addDoctor(doctor);
        System.out.println("Doctor added successfully!");
    }

    private static void viewDoctorById() {
        String id = readString("Enter doctor ID: ");

        doctorService.getDoctorByID(id)
                .ifPresentOrElse(doctorService::viewDoctor,
                        () -> System.out.println("Doctor not found.")
                );
    }

    private static void updateDoctorName() {
        String id = readString("Enter doctor ID: ");
        String newName = readString("Enter new name: ");

        boolean updated = doctorService.updateDoctorName(id, newName);
        System.out.println(updated ? "Updated!" : "Doctor not found.");
    }

    private static void deleteDoctor() {
        String id = readString("Enter doctor ID: ");
        boolean deleted = doctorService.deleteDoctor(id);
        System.out.println(deleted ? "Deleted!" : "Doctor not found.");
    }

    private static void searchDoctor() {
        String keyword = readString("Enter name/specialization keyword: ");
        showDoctors(doctorService.searchByKeyword(keyword));
    }

    private static void showDoctors(Collection<Doctor> doctors) {
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }

        doctors.forEach(doctorService::viewDoctor);
    }

    // ---------------------------------------------------------
    // PATIENT MENU
    // ---------------------------------------------------------
    private static void patientMenu() {
        boolean loop = true;

        while (loop) {
            System.out.println("\n====== Patient Management ======");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patient By ID");
            System.out.println("3. View All Patients");
            System.out.println("4. Update Patient Name");
            System.out.println("5. Delete Patient");
            System.out.println("6. Search Patients");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addPatient();
                    case 2 -> viewPatientById();
                    case 3 -> showPatients(patientService.getPatients());
                    case 4 -> updatePatientName();
                    case 5 -> deletePatient();
                    case 6 -> searchPatient();
                    case 0 -> loop = false;
                    default -> System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void addPatient() {
        String id = readString("Enter patient ID: ");
        String name = readString("Enter name: ");
        String email = readString("Enter email: ");
        Long phone = readLong("Enter phone number: ");
        String address = readString("Enter address: ");

        LocalDate dob = readDateOnly("Enter DOB (yyyy-MM-dd): ");

        System.out.println("Select Gender: 1.MALE  2.FEMALE  3.OTHER");
        int g = readInt("Enter: ");
        Gender gender = switch (g) {
            case 1 -> Gender.MALE;
            case 2 -> Gender.FEMALE;
            case 3 -> Gender.OTHERS;
            default -> throw new IllegalArgumentException("Invalid gender selection!");
        };

        Patient patient = new Patient(
                name, email, phone, address, dob, gender, id
        );

        patientService.addPatient(patient);
        System.out.println("Patient added successfully!");
    }

    private static void viewPatientById() {
        String id = readString("Enter patient ID: ");

        patientService.getPatientByID(id)
                .ifPresentOrElse(
                        patientService::viewPatient,
                        () -> System.out.println("Patient not found.")
                );
    }

    private static void updatePatientName() {
        String id = readString("Enter patient ID: ");
        String newName = readString("Enter new name: ");
        patientService.updatePatientName(id, newName);
        System.out.println("Updated successfully!");
    }

    private static void deletePatient() {
        String id = readString("Enter patient ID: ");
        boolean deleted = patientService.deletePatient(id);
        System.out.println(deleted ? "Deleted!" : "Patient not found.");
    }

    private static void searchPatient() {
        String keyword = readString("Enter name keyword: ");
        showPatients(patientService.searchByKeyword(keyword));
    }

    private static void showPatients(Collection<Patient> patients) {
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        patients.forEach(patientService::viewPatient);
    }

    // ---------------------------------------------------------
    // BILLING MENU
    // ---------------------------------------------------------
    private static void billingMenu() {
        boolean loop = true;

        while (loop) {
            System.out.println("\n====== Billing Management ======");
            System.out.println("1. Add Bill");
            System.out.println("2. View All Bills");
            System.out.println("3. Generate Billing Summary");
            System.out.println("4. Mark Payment as Paid");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addBill();
                    case 2 -> showBills(billingService.getAllBills());
                    case 3 -> billingSummary();
                    case 4 -> markPayment();
                    case 0 -> loop = false;
                    default -> System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void addBill() {
        Long billId = readLong("Enter bill ID: ");
        Long patientId = readLong("Enter patient ID: ");
        double amount = readDouble("Enter amount: ");

        Bill bill = new Bill(billId, patientId, amount, LocalDateTime.now(), PaymentStatus.PENDING);
        billingService.addBill(bill);

        System.out.println("Bill added successfully!");
    }

    private static void billingSummary() {
        Long billId = readLong("Enter bill ID: ");

        System.out.println("Choose Billing Strategy:");
        System.out.println("1. GST Billing (18%)");
        System.out.println("2. Senior Citizen Billing (5%)");
        int choice = readInt("Enter: ");

        var strategy = switch (choice) {
            case 1 -> new GSTBillingStrategy();
            case 2 -> new SeniorCitizenBillingStategy();
            default -> throw new IllegalArgumentException("Invalid strategy!");
        };

        BillingSummary summary = billingService.generateBillingSummary(billId, strategy);

        System.out.println("\n=== Billing Summary ===");
        System.out.println("BillID: " + summary.getBillId() + " " + summary.getFinalAmount() + " " + summary.getTaxStrategyUsed());
    }

    private static void markPayment() {
        Long billId = readLong("Enter bill ID: ");
        billingService.paymentsMade(billId);
        System.out.println("Payment marked as PAID.");
    }

    private static void showBills(Collection<Bill> bills) {
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
            return;
        }
        bills.forEach(billingService::viewBill);
    }

    // ---------------------------------------------------------
    // INPUT HELPERS
    // ---------------------------------------------------------
    private static int readInt(String msg) {
        System.out.print(msg);
        return Integer.parseInt(scanner.nextLine());
    }

    private static Long readLong(String msg) {
        System.out.print(msg);
        return Long.parseLong(scanner.nextLine());
    }

    private static double readDouble(String msg) {
        System.out.print(msg);
        return Double.parseDouble(scanner.nextLine());
    }

    private static String readString(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private static LocalDateTime readDate(String msg) {
        System.out.print(msg);
        return LocalDateTime.parse(scanner.nextLine(), DATE_FORMAT);
    }

    private static LocalDate readDateOnly(String msg) {
        System.out.print(msg);
        return LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
