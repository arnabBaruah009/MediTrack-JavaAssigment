package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.PaymentStatus;
import com.airtribe.meditrack.entity.Bill;

import java.time.LocalDateTime;

public class BillValidator {
    public static void validate(Bill bill) {

        if (bill == null) {
            throw new IllegalArgumentException("Bill object cannot be null.");
        }

        if (bill.getBillId() == null || bill.getBillId() <= 0) {
            throw new IllegalArgumentException("Bill ID must be a positive number.");
        }

        if (bill.getPatientId() == null || bill.getPatientId() <= 0) {
            throw new IllegalArgumentException("Patient ID must be a positive number.");
        }

        if (bill.getAmount() == null || bill.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        if (bill.getBillingDate() == null) {
            throw new IllegalArgumentException("Billing date cannot be null.");
        }

        if (bill.getBillingDate().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Billing date cannot be in the future.");
        }

        if (bill.getPaymentStatus() == null) {
            throw new IllegalArgumentException("Payment status cannot be null.");
        }

        validatePaymentStatus(bill.getPaymentStatus());
    }

    private static void validatePaymentStatus(PaymentStatus paymentStatus) {
        if (paymentStatus != PaymentStatus.PAID &&
                paymentStatus != PaymentStatus.PENDING &&
                paymentStatus != PaymentStatus.UNPAID) {
            throw new IllegalArgumentException("Invalid payment status: " + paymentStatus);
        }
    }
}
