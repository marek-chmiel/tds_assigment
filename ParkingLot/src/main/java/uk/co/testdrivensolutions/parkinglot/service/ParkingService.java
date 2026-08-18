package uk.co.testdrivensolutions.parkinglot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.testdrivensolutions.parkinglot.dto.BillVehicleResponseDTO;
import uk.co.testdrivensolutions.parkinglot.dto.ParkVehicleResponseDTO;
import uk.co.testdrivensolutions.parkinglot.dto.ParkingStatusDTO;
import uk.co.testdrivensolutions.parkinglot.exception.ParkingFullException;
import uk.co.testdrivensolutions.parkinglot.exception.VehicleAlreadyParkedException;
import uk.co.testdrivensolutions.parkinglot.exception.VehicleNotFoundException;
import uk.co.testdrivensolutions.parkinglot.model.ParkingSession;
import uk.co.testdrivensolutions.parkinglot.model.ParkingSpace;
import uk.co.testdrivensolutions.parkinglot.model.VehicleType;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSessionRepository;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSpaceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ParkingService {

    private final ParkingBillingService parkingBillingService;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final ParkingSessionRepository parkingSessionRepository;

    public ParkingService(ParkingSpaceRepository parkingSpaceRepository, ParkingSessionRepository parkingSessionRepository, ParkingBillingService parkingBillingService) {
        this.parkingSpaceRepository = parkingSpaceRepository;
        this.parkingSessionRepository = parkingSessionRepository;
        this.parkingBillingService = parkingBillingService;
    }

    public ParkingStatusDTO getParkingStatus() {
        return new ParkingStatusDTO(parkingSpaceRepository.countByOccupiedFalse(), parkingSpaceRepository.countByOccupiedTrue());
    }

    @Transactional
    public ParkVehicleResponseDTO parkVehicle(String vehicleReg, VehicleType vehicleType) {
        if(parkingSessionRepository.existsByVehicleRegAndTimeOutIsNull(vehicleReg)) {
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
        ParkingSession savedParkingSession = parkingSessionRepository.save(parkingSession);

        return new ParkVehicleResponseDTO(savedParkingSession.getVehicleReg(), savedParkingSession.getParkingSpace().getId(), savedParkingSession.getTimeIn());
    }

    @Transactional
    public BillVehicleResponseDTO billVehicle(String vehicleReg) {
        ParkingSession parkingSession = parkingSessionRepository.findByVehicleRegAndTimeOutIsNull(vehicleReg)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle  %s not found".formatted(vehicleReg)));

        ParkingSpace parkingSpace = parkingSession.getParkingSpace();
        parkingSpace.setOccupied(false);

        parkingSession.setBillId(UUID.randomUUID().toString());
        parkingSession.setTimeOut(LocalDateTime.now());
        BigDecimal vehicleCharge = parkingBillingService.calculateTotalCharge(parkingSession.getVehicleType(), parkingSession.getTimeIn(), parkingSession.getTimeOut());
        parkingSession.setVehicleCharge(vehicleCharge);

        ParkingSession updatedParkingSession =parkingSessionRepository.save(parkingSession);

        return new BillVehicleResponseDTO(updatedParkingSession.getBillId(), updatedParkingSession.getVehicleReg(), updatedParkingSession.getVehicleCharge().doubleValue(), updatedParkingSession.getTimeIn(), updatedParkingSession.getTimeOut());
    }
}
