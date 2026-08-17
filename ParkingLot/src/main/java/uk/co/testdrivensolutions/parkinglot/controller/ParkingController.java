package uk.co.testdrivensolutions.parkinglot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.testdrivensolutions.parkinglot.dto.ParkingStatusDTO;
import uk.co.testdrivensolutions.parkinglot.service.ParkingSpaceService;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingSpaceService parkingSpaceService;

    public ParkingController(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    @GetMapping
    public ParkingStatusDTO getStatus() {
        return parkingSpaceService.getParkingStatus();
    }
}
