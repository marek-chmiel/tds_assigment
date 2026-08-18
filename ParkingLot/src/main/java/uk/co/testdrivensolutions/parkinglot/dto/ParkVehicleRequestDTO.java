package uk.co.testdrivensolutions.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uk.co.testdrivensolutions.parkinglot.model.VehicleType;

public record ParkVehicleRequestDTO(@NotBlank(message = "Vehicle registration cannot be blank") String vehicleReg, @NotNull(message = "Vehicle type is required") VehicleType vehicleType) {
}
