package uk.co.testdrivensolutions.parkinglot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.co.testdrivensolutions.parkinglot.model.ParkingSession;

@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Integer> {
       boolean existsByVehicleReg(String vehicleRegistrationNumber);
}
