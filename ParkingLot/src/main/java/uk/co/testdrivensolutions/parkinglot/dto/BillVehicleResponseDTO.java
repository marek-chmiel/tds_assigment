package uk.co.testdrivensolutions.parkinglot.dto;

import java.time.LocalDateTime;

public record BillVehicleResponseDTO(String billId, String vehicleReg, double vehicleCharge, LocalDateTime timeIn, LocalDateTime timeOut) {
}
