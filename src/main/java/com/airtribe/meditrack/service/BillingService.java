package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.PaymentStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillingSummary;
import com.airtribe.meditrack.interfaces.BillingStrategy;
import com.airtribe.meditrack.util.Validator;

import java.util.*;

public class BillingService {
    private final List<Bill> bills = new ArrayList<>();

    public void addBill(Bill bill) {
        Validator.validateBill(bill);
        bills.add(bill);
    }

    public Collection<Bill> getAllBills() {
        return Collections.unmodifiableList(bills);
    }

    public BillingSummary generateBillingSummary(Long billId, BillingStrategy billingStrategy) {
        Bill bill = bills.stream()
                .filter(b -> b.getBillId().equals(billId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Bill not found"));

        double tax = billingStrategy.calculateTax(bill);
        double finalAmount = bill.getAmount() + tax;

        return new BillingSummary(billId, bill.getAmount(), tax, finalAmount, billingStrategy.getStrategyName());
    }

    public void paymentsMade(Long billId) {
        Optional<Bill> bill = bills.stream()
                .filter(b -> b.getBillId().equals(billId))
                .findFirst();
        bill.ifPresent(value -> value.setPaymentStatus(PaymentStatus.PAID));
    }
}
