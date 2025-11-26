package com.airtribe.meditrack.util.billingstrategy;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.interfaces.BillingStrategy;

public class GSTBillingStrategy implements BillingStrategy {
    private static final double GST_RATE = 0.18;

    @Override
    public double calculateTax(Bill bill) {
        return bill.getAmount() * GST_RATE;
    }

    @Override
    public String getStrategyName() {
        return "GST Billing Strategy";
    }
}