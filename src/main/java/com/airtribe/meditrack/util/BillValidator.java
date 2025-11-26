package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.PaymentStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.exceptions.InvalidBillingException;

import java.time.LocalDateTime;

public class BillValidator {
    public static void validate(Bill bill) {

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
