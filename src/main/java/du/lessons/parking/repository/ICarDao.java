package du.lessons.parking.repository;

import du.lessons.parking.model.Car;
import du.lessons.parking.model.CarImage;

import java.util.Optional;

public interface ICarDao {

    public void save(Car car);

    void update(Car car);

    Optional<Car> getById(Long id);

    void removeImage(CarImage img);

    void delete(Long id);
}
