package uk.co.testdrivensolutions.parkinglot.dto;

import java.time.LocalDateTime;

public record ParkVehicleResponseDTO(String vehicleReg, int spaceNumber, LocalDateTime timeIn) {
}
