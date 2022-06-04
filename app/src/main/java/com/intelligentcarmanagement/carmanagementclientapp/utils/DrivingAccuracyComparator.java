package com.intelligentcarmanagement.carmanagementclientapp.utils;

import com.intelligentcarmanagement.carmanagementclientapp.models.driver.Driver;

import java.util.Comparator;

public class DrivingAccuracyComparator implements Comparator<Driver> {
    @Override
    public int compare(Driver d1, Driver d2) {
        return Double.compare(d1.getAccuracy(), d2.getAccuracy());
    }
}
