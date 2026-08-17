package uk.co.testdrivensolutions.parkinglot.service;

import org.springframework.stereotype.Service;
import uk.co.testdrivensolutions.parkinglot.dto.ParkingStatusDTO;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSpaceRepository;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    public ParkingStatusDTO getParkingStatus() {
        return new ParkingStatusDTO(parkingSpaceRepository.countByOccupiedFalse(), parkingSpaceRepository.countByOccupiedTrue());
    }
}
