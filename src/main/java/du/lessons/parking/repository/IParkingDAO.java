package du.lessons.parking.repository;

import du.lessons.parking.model.Parking;

import java.util.Optional;

public interface IParkingDAO {

    void save(Parking parking);

    Optional<Parking> getById(Long id);
}
