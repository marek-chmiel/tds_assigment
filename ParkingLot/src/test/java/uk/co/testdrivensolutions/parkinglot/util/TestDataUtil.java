package uk.co.testdrivensolutions.parkinglot.util;

import uk.co.testdrivensolutions.parkinglot.model.ParkingSpace;

import java.util.ArrayList;
import java.util.List;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static List<ParkingSpace> createParkingSpaces(int allSpacesCount) {
        List<ParkingSpace> parkingSpaces = new ArrayList<>();
        for (int i = 1; i <= allSpacesCount; i++) {
            ParkingSpace parkingSpace = new ParkingSpace();
            parkingSpace.setId(i);
            parkingSpace.setOccupied(false);
            parkingSpaces.add(parkingSpace);
        }
        return parkingSpaces;
    }
}
