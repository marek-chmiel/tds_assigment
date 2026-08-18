package uk.co.testdrivensolutions.parkinglot.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.testdrivensolutions.parkinglot.dto.*;
import uk.co.testdrivensolutions.parkinglot.service.ParkingService;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    public ParkingStatusDTO getStatus() {
        return parkingService.getParkingStatus();
    }

    @PostMapping
    public ResponseEntity<ParkVehicleResponseDTO> parkVehicle(@Valid @RequestBody ParkVehicleRequestDTO request) {
        ParkVehicleResponseDTO response = parkingService.parkVehicle(request.vehicleReg(), request.vehicleType());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/bill")
    public ResponseEntity<BillVehicleResponseDTO> billVehicle(@Valid @RequestBody BillVehicleRequestDTO request) {
        BillVehicleResponseDTO response = parkingService.billVehicle(request.vehicleReg());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
