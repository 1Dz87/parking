package du.lessons.parking.service;

import du.lessons.parking.lib.dto.CarDTO;
import du.lessons.parking.lib.exceptions.CarNotFoundException;
import du.lessons.parking.model.Car;
import du.lessons.parking.model.CarBody;
import du.lessons.parking.model.EngineType;
import du.lessons.parking.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ICarService {

    public Car createCar(User user, String model, CarBody body, EngineType engineType, Float engineValue, MultipartFile carImage) throws IOException;

    Car getById(Long id) throws CarNotFoundException;

    Car updateCar(Long carId, String model, CarBody body, EngineType engineType, Float engineValue, MultipartFile carImage) throws IOException, CarNotFoundException;

    Long addUserCar(User user, Map<String, Object> carVo);

    void updateCar(Long id, CarDTO carDto) throws CarNotFoundException;

    void delete(Long id);
}
