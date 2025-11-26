package com.airtribe.meditrack.interfaces;

import com.airtribe.meditrack.entity.Bill;

public interface BillingStrategy {
    double calculateTax(Bill bill);

    String getStrategyName();
}
