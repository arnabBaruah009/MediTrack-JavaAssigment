package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.PaymentStatus;

public class Bill {
    private Long billId;
    private Long patientId;
    private Double amount;
    private String billingDate;
    private PaymentStatus paymentStatus;

    public Bill(Long billId, Long patientId, Double amount, String billingDate, PaymentStatus paymentStatus) {
        this.billId = billId;
        this.patientId = patientId;
        this.amount = amount;
        this.billingDate = billingDate;
        this.paymentStatus = paymentStatus;
    }

    public Bill() {}

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
