package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.PaymentStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.exceptions.InvalidBillingException;

import java.time.LocalDateTime;

public class Validator {
    private static boolean isValidSlot(LocalDateTime dateTime) {
        int minute = dateTime.getMinute();
        return minute == 0 || minute == 30;
    }

    public static void validateAppointment(Long patientId, Long doctorId, LocalDateTime dateTime) {

        if (patientId == null || patientId <= 0)
            throw new IllegalArgumentException("Patient ID is invalid");

        if (doctorId == null || doctorId <= 0)
            throw new IllegalArgumentException("Doctor ID is invalid");

        if (dateTime == null)
            throw new IllegalArgumentException("Appointment date cannot be null");

        if (dateTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Appointment date cannot be in the past");

        if (!isValidSlot(dateTime))
            throw new IllegalArgumentException("Appointments must be on the hour or half-hour");
    }

    public static void validateBill(Bill bill) {

        if (bill == null) {
            throw new InvalidBillingException("Bill object cannot be null.");
        }

        if (bill.getBillId() == null || bill.getBillId() <= 0) {
            throw new InvalidBillingException("Bill ID must be a positive number.");
        }

        if (bill.getPatientId() == null || bill.getPatientId() <= 0) {
            throw new InvalidBillingException("Patient ID must be a positive number.");
        }

        if (bill.getAmount() == null || bill.getAmount() <= 0) {
            throw new InvalidBillingException("Amount must be greater than 0.");
        }

        if (bill.getBillingDate() == null) {
            throw new InvalidBillingException("Billing date cannot be null.");
        }

        if (bill.getBillingDate().isAfter(LocalDateTime.now())) {
            throw new InvalidBillingException("Billing date cannot be in the future.");
        }

        if (bill.getPaymentStatus() == null) {
            throw new InvalidBillingException("Payment status cannot be null.");
        }

        validatePaymentStatus(bill.getPaymentStatus());
    }

    private static void validatePaymentStatus(PaymentStatus paymentStatus) {
        if (paymentStatus != PaymentStatus.PAID &&
                paymentStatus != PaymentStatus.PENDING &&
                paymentStatus != PaymentStatus.UNPAID) {
            throw new InvalidBillingException("Invalid payment status: " + paymentStatus);
        }
    }
}
