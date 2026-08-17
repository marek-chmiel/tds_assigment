package uk.co.testdrivensolutions.parkinglot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.co.testdrivensolutions.parkinglot.dto.ParkingStatusDTO;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSpaceRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.testdrivensolutions.parkinglot.util.TestDataUtil.createParkingSpaces;

@SpringBootTest
@ActiveProfiles("test")
public class ParkingSpaceIntegrationTest {
    private final static int PARKING_SPACE_COUNT = 10;
    private final static int PARKING_SPACE_OCCUPIED_COUNT = 4;

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @BeforeEach
    void setUp() {
        parkingSpaceRepository.saveAll(createParkingSpaces(PARKING_SPACE_COUNT, PARKING_SPACE_OCCUPIED_COUNT));
    }

    @AfterEach
    void tearDown() {
        parkingSpaceRepository.deleteAll();
    }

    @Test
    void shouldFetchParkingSpaceStatus() {
        // When
        ParkingStatusDTO parkingStatus = parkingSpaceService.getParkingStatus();

        // Then
        assertThat(parkingStatus.availableSpaces()).isEqualTo(PARKING_SPACE_COUNT - PARKING_SPACE_OCCUPIED_COUNT);
        assertThat(parkingStatus.occupiedSpaces()).isEqualTo(PARKING_SPACE_OCCUPIED_COUNT);
    }


}
