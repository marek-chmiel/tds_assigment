package uk.co.testdrivensolutions.parkinglot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.testdrivensolutions.parkinglot.dto.ParkVehicleResponseDTO;
import uk.co.testdrivensolutions.parkinglot.dto.ParkingStatusDTO;
import uk.co.testdrivensolutions.parkinglot.exception.ParkingFullException;
import uk.co.testdrivensolutions.parkinglot.exception.VehicleAlreadyParkedException;
import uk.co.testdrivensolutions.parkinglot.model.ParkingSession;
import uk.co.testdrivensolutions.parkinglot.model.ParkingSpace;
import uk.co.testdrivensolutions.parkinglot.model.VehicleType;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSessionRepository;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSpaceRepository;

import java.time.LocalDateTime;

@Service
public class ParkingService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingSessionRepository parkingSessionRepository;

    public ParkingService(ParkingSpaceRepository parkingSpaceRepository, ParkingSessionRepository parkingSessionRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.parkingSessionRepository = parkingSessionRepository;
    }

    public ParkingStatusDTO getParkingStatus() {
        return new ParkingStatusDTO(parkingSpaceRepository.countByOccupiedFalse(), parkingSpaceRepository.countByOccupiedTrue());
    }

    @Transactional
    public ParkVehicleResponseDTO parkVehicle(String vehicleReg, VehicleType vehicleType) {
        if(parkingSessionRepository.existsByVehicleReg(vehicleReg)) {
            throw new VehicleAlreadyParkedException("Vehicle %s is already parked".formatted(vehicleReg));
        }

        ParkingSpace space = parkingSpaceRepository.findFirstByOccupiedFalseOrderByIdAsc()
                .orElseThrow(() -> new ParkingFullException("Vehicle %s is not allowed to park".formatted(vehicleReg)));
        space.setOccupied(true);

        ParkingSession parkingSession = new ParkingSession();
        parkingSession.setVehicleReg(vehicleReg);
        parkingSession.setVehicleType(vehicleType);
        parkingSession.setTimeIn(LocalDateTime.now());
        parkingSession.setParkingSpace(space);
        parkingSessionRepository.save(parkingSession);

        return new ParkVehicleResponseDTO(parkingSession.getVehicleReg(), parkingSession.getParkingSpace().getId(), parkingSession.getTimeIn());
    }
}
