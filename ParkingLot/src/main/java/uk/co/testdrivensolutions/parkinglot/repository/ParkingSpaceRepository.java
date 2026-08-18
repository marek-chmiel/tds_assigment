package uk.co.testdrivensolutions.parkinglot.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import uk.co.testdrivensolutions.parkinglot.model.ParkingSpace;

import java.util.Optional;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Integer> {

    int countByOccupiedFalse();

    int countByOccupiedTrue();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ParkingSpace> findFirstByOccupiedFalseOrderByIdAsc();
}
