package uk.co.testdrivensolutions.parkinglot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum VehicleType {
    SMALL(1),
    MEDIUM(2),
    LARGE(3);

    private final int type;

    VehicleType(int vehicleType) {
        this.type = vehicleType;
    }

    @JsonValue
    public int getType() {
        return type;
    }

    @JsonCreator
    public static VehicleType fromType(Integer type) {
        if (type == null) {
            return null;
        }
        return Arrays.stream(VehicleType.values())
                .filter(vehicleType -> vehicleType.type == type)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle type: " + type));
    }
}
