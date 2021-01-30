package du.lessons.parking.repository.impl;

import du.lessons.parking.model.Car;
import du.lessons.parking.repository.ICarDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class CarDao implements ICarDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void save(Car car) {
        manager.persist(car);
    }
}
