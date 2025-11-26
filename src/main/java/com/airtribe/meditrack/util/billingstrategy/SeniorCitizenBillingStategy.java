package com.airtribe.meditrack.util.billingstrategy;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.interfaces.BillingStrategy;

public class SeniorCitizenBillingStategy implements BillingStrategy {
    private static final double SENIOR_CITIZEN_RATE = 0.05;

    @Override
    public double calculateTax(Bill bill) {
        return bill.getAmount() * SENIOR_CITIZEN_RATE;
    }

    @Override
    public String getStrategyName() {
        return "Senior Citizen Billing Strategy";
    }
}
