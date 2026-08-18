package uk.co.testdrivensolutions.parkinglot.service;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import uk.co.testdrivensolutions.parkinglot.model.VehicleType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ParkingBillingService {
    private static final BigDecimal ADDITIONAL_CHARGE_PER_THRESHOLD = new BigDecimal("1.00");
    private static final int ADDITIONAL_CHARGE_THRESHOLD_IN_MINUTES=5;

    public BigDecimal calculateTotalCharge(VehicleType vehicleType, LocalDateTime timeIn, LocalDateTime timeOut) {
        long minutesParked = ChronoUnit.MINUTES.between(timeIn, timeOut);
        BigDecimal ratePerMinute = getRatePerMinute(vehicleType);
        BigDecimal baseCharge = ratePerMinute.multiply(BigDecimal.valueOf(minutesParked));
        long additionalChargeBlocks = minutesParked / ADDITIONAL_CHARGE_THRESHOLD_IN_MINUTES;
        BigDecimal additionalCharge = ADDITIONAL_CHARGE_PER_THRESHOLD.multiply(BigDecimal.valueOf(additionalChargeBlocks));
        return baseCharge.add(additionalCharge).setScale(2, RoundingMode.HALF_UP);
    }

    private static @NonNull BigDecimal getRatePerMinute(VehicleType vehicleType) {
        return switch (vehicleType) {
            case SMALL -> new BigDecimal("0.10");
            case MEDIUM -> new BigDecimal("0.20");
            case LARGE -> new BigDecimal("0.40");
        };
    }
}
