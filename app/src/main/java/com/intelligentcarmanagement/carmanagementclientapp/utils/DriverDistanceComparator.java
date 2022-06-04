package com.intelligentcarmanagement.carmanagementclientapp.utils;

import com.google.android.gms.maps.model.LatLng;
import com.intelligentcarmanagement.carmanagementclientapp.models.driver.Driver;

import java.util.Comparator;

public class DriverDistanceComparator implements Comparator<Driver> {
    private LatLng pickUpLatLng = null;

    public DriverDistanceComparator(LatLng pickUpLatLng)
    {
        this.pickUpLatLng = pickUpLatLng;
    }

    @Override
    public int compare(Driver d1, Driver d2) {
        double distance1 = HaversineAlgorithm.HaversineInKM(
                pickUpLatLng.latitude,
                pickUpLatLng.longitude,
                d1.getCurrentLat() == null ? pickUpLatLng.latitude : Double.parseDouble(d1.getCurrentLat()),
                d1.getCurrentLong() == null ? pickUpLatLng.longitude : Double.parseDouble(d1.getCurrentLong())
        );

        double distance2 = HaversineAlgorithm.HaversineInKM(
                pickUpLatLng.latitude,
                pickUpLatLng.longitude,
                d2.getCurrentLat() == null ? pickUpLatLng.latitude : Double.parseDouble(d2.getCurrentLat()),
                d2.getCurrentLong() == null ? pickUpLatLng.longitude : Double.parseDouble(d2.getCurrentLong())
        );


        return Double.compare(distance1, distance2);
    }
}
