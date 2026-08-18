package uk.co.testdrivensolutions.parkinglot.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import uk.co.testdrivensolutions.parkinglot.model.VehicleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class ParkingBillingServiceTest {

    private final ParkingBillingService parkingBillingService = new ParkingBillingService();

    @Test
    void testSmallVehicleFor4Minutes() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeOut = now.plusMinutes(4);

        // When
        BigDecimal charge = parkingBillingService.calculateTotalCharge(VehicleType.SMALL, now, timeOut);

        // Then
        assertEquals(new BigDecimal("0.40"), charge);
    }

    @Test
    void testMediumVehicleFor5Minutes() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeOut = now.plusMinutes(5);

        // When
        BigDecimal charge = parkingBillingService.calculateTotalCharge(VehicleType.MEDIUM, now, timeOut);

        // Then
        assertEquals(new BigDecimal("2.00"), charge);
    }

    @Test
    void testLargeVehicleFor12Minutes() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime timeOut = now.plusMinutes(12);

        // When
        BigDecimal charge = parkingBillingService.calculateTotalCharge(VehicleType.LARGE, now, timeOut);

        // Then
        assertEquals(new BigDecimal("6.80"), charge);
    }
}
