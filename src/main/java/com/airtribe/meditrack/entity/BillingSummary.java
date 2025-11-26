package com.airtribe.meditrack.entity;

public class BillingSummary {
    private Long billId;
    private Double baseAmount;
    private Double taxAmount;
    private Double finalAmount;
    private String taxStrategyUsed;

    public BillingSummary(Long billId, Double baseAmount, Double taxAmount, Double finalAmount, String taxStrategyUsed) {
        this.billId = billId;
        this.baseAmount = baseAmount;
        this.taxAmount = taxAmount;
        this.finalAmount = finalAmount;
        this.taxStrategyUsed = taxStrategyUsed;
    }

    public BillingSummary() {
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getTaxStrategyUsed() {
        return taxStrategyUsed;
    }

    public void setTaxStrategyUsed(String taxStrategyUsed) {
        this.taxStrategyUsed = taxStrategyUsed;
    }
}
