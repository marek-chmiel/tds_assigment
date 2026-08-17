package uk.co.testdrivensolutions.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.co.testdrivensolutions.parkinglot.model.ParkingSpace;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Integer> {

    int countByOccupiedFalse();

    int countByOccupiedTrue();
}
