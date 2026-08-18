package uk.co.testdrivensolutions.parkinglot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.co.testdrivensolutions.parkinglot.dto.ParkingStatusDTO;
import uk.co.testdrivensolutions.parkinglot.model.VehicleType;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSessionRepository;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSpaceRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.testdrivensolutions.parkinglot.util.TestDataUtil.createParkingSpaces;

@SpringBootTest
@ActiveProfiles("test")
public class ParkingServiceIntegrationTest {
    private final static int PARKING_SPACE_COUNT = 10;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private ParkingSessionRepository parkingSessionRepository;

    @Autowired
    private ParkingService parkingService;

    @BeforeEach
    void setUp() {
        parkingSpaceRepository.saveAll(createParkingSpaces(PARKING_SPACE_COUNT));
    }

    @AfterEach
    void tearDown() {
        parkingSessionRepository.deleteAll();
        parkingSpaceRepository.deleteAll();
    }

    @Test
    void shouldFetchEmptyParkingSpaceStatus() {
        // When
        ParkingStatusDTO parkingStatus = parkingService.getParkingStatus();

        // Then
        assertThat(parkingStatus.availableSpaces()).isEqualTo(PARKING_SPACE_COUNT);
        assertThat(parkingStatus.occupiedSpaces()).isEqualTo(0);
    }

    @Test
    void shouldFetchParkingSpaceStatusWithOneVehicleParked() {
        // Given
        parkingService.parkVehicle("DUMMY_REG", VehicleType.SMALL);

        // When
        ParkingStatusDTO parkingStatus = parkingService.getParkingStatus();

        // Then
        assertThat(parkingStatus.availableSpaces()).isEqualTo(PARKING_SPACE_COUNT - 1);
        assertThat(parkingStatus.occupiedSpaces()).isEqualTo(1);
    }

    @Test
    void shouldFetchParkingSpaceStatusAfterOneVehicleBilled() {
        // Given
        String dummyReg = "DUMMY_REG";
        parkingService.parkVehicle(dummyReg, VehicleType.SMALL);
        parkingService.billVehicle(dummyReg);

        // When
        ParkingStatusDTO parkingStatus = parkingService.getParkingStatus();

        // Then
        assertThat(parkingStatus.availableSpaces()).isEqualTo(PARKING_SPACE_COUNT);
        assertThat(parkingStatus.occupiedSpaces()).isEqualTo(0);
    }
}
