package uk.co.testdrivensolutions.parkinglot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import uk.co.testdrivensolutions.parkinglot.dto.ParkVehicleResponseDTO;
import uk.co.testdrivensolutions.parkinglot.exception.VehicleAlreadyParkedException;
import uk.co.testdrivensolutions.parkinglot.model.ParkingSpace;
import uk.co.testdrivensolutions.parkinglot.model.VehicleType;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSessionRepository;
import uk.co.testdrivensolutions.parkinglot.repository.ParkingSpaceRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ParkingServiceTest {
    @Mock
    private ParkingSpaceRepository parkingSpaceRepository;

    @Mock
    private ParkingSessionRepository parkingSessionRepository;

    @InjectMocks
    private ParkingService parkingService;

    @Test
    void shouldNotAllowToParkTwiceWithTheSameVehicle() {
        //Given
        String vehicleReg = "DUMMY_REG";
        given(parkingSessionRepository.existsByVehicleReg(vehicleReg)).willReturn(true);

        // When
        assertThatExceptionOfType(VehicleAlreadyParkedException.class)
                .isThrownBy(() -> parkingService.parkVehicle(vehicleReg, VehicleType.SMALL));

    }

    @Test
    void shouldParSuccessfully() {
        //Given
        String vehicleReg = "DUMMY_REG";
        given(parkingSessionRepository.existsByVehicleReg(vehicleReg)).willReturn(false);

        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setId(1);
        parkingSpace.setOccupied(false);
        given(parkingSpaceRepository.findFirstByOccupiedFalseOrderByIdAsc()).willReturn(Optional.of(parkingSpace));

        // When

        ParkVehicleResponseDTO parkVehicleResponseDTO = parkingService.parkVehicle(vehicleReg, VehicleType.SMALL);

        assertThat(parkVehicleResponseDTO).isNotNull();
        assertThat(parkVehicleResponseDTO.vehicleReg()).isEqualTo(vehicleReg);
        assertThat(parkVehicleResponseDTO.spaceNumber()).isEqualTo(parkingSpace.getId());

    }

    @AfterEach
    void tearDown() {
        Mockito.reset(parkingSessionRepository);
        Mockito.reset(parkingSpaceRepository);
    }
}
