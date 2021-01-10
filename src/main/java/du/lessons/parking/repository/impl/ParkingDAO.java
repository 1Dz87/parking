package du.lessons.parking.repository.impl;

import du.lessons.parking.model.Parking;
import du.lessons.parking.repository.IParkingDAO;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class ParkingDAO implements IParkingDAO {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void save(Parking parking) {
        manager.persist(parking);
    }

    @Override
    public Optional<Parking> getById(Long id) {
        Parking p = manager.find(Parking.class, id);
        if (p != null) {
            p.getParkingAreas().forEach(area -> {
                Hibernate.initialize(area);
                area.getPlaces().forEach(Hibernate::initialize);
            });
        }
        return Optional.of(p);
    }


}
