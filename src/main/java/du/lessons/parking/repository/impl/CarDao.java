package du.lessons.parking.repository.impl;

import du.lessons.parking.model.Car;
import du.lessons.parking.model.CarImage;
import du.lessons.parking.repository.ICarDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@Transactional
public class CarDao implements ICarDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void save(Car car) {
        manager.persist(car);
    }

    @Override
    public Optional<Car> getById(Long id) {
        return Optional.of(manager.find(Car.class, id));
    }

    @Override
    public void removeImage(CarImage img) {
        manager.remove(img);
    }

    @Override
    public void update(Car car) {
        Session session = manager.unwrap(Session.class);
        session.saveOrUpdate(car);
    }

}
