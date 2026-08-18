package uk.co.testdrivensolutions.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;

public record BillVehicleRequestDTO(@NotBlank(message = "Vehicle registration cannot be blank") String vehicleReg) {
}
