package du.lessons.parking.service.impl;

import du.lessons.parking.lib.dto.CarDTO;
import du.lessons.parking.lib.exceptions.CarNotFoundException;
import du.lessons.parking.model.*;
import du.lessons.parking.repository.ICarDao;
import du.lessons.parking.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Service
public class CarService implements ICarService {

    private final ICarDao carDao;

    @Autowired
    public CarService(ICarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public Car createCar(User user, String model, CarBody body, EngineType engineType, Float engineValue, MultipartFile carImage) throws IOException {
        Car car = new Car();
        car.setModel(model);
        car.setBody(body);
        car.setUser(user);
        car.setEngineValue(engineValue);
        car.setType(engineType);
        CarImage img = getImage(carImage);
        img.setCar(car);
        car.setImage(img);
        carDao.save(car);
        return car;
    }

    @Override
    public Car getById(Long id) throws CarNotFoundException {
        return carDao.getById(id).orElseThrow(CarNotFoundException::new);
    }

    @Override
    @Transactional
    public Car updateCar(Long carId, String model, CarBody body, EngineType engineType, Float engineValue, MultipartFile carImage) throws IOException, CarNotFoundException {
        Car car = getById(carId);
        car.setModel(model);
        car.setBody(body);
        car.setEngineValue(engineValue);
        car.setType(engineType);
        if (carImage != null) {
            if (car.getImage() != null) {
                carDao.removeImage(car.getImage());
            }
            CarImage img = getImage(carImage);
            img.setCar(car);
            car.setImage(img);
        }
        carDao.update(car);
        return car;
    }

    /**
     *
     * @param user - пользователь, которому принадлежит авто
     * @param carVo - набор полей со значениями для создания автомобиля
     * @return - первичный ключ созданного автомобиля
     */
    @Override
    public Long addUserCar(User user, Map<String, Object> carVo) {
        Car car = new Car();
        for (Map.Entry<String, Object> entry : carVo.entrySet()) {
            String key = entry.getKey(); // 1. model 2. body...
            Object value = entry.getValue(); // 1. Bentley 2. sedan...
            try {
                Field field = Car.class.getDeclaredField(key);
                switch (field.getName()) {
                    case "model":
                        car.setModel((String) value);
                        break;
                    case "body":
                        car.setBody(CarBody.valueOf(((String) value).toUpperCase()));
                        break;
                    case "engineValue":
                        String strVal = String.valueOf(value);
                        BigDecimal bd = new BigDecimal(strVal);
                        car.setEngineValue(bd.floatValue());
                        break;
                    case "type":
                        car.setType(EngineType.valueOf(((String) value).toUpperCase()));
                        break;
                    default: break;
                }
            } catch (NoSuchFieldException e) {
                //TODO: logger
                e.printStackTrace();
            }
        }
        car.setUser(user);
        carDao.save(car);
        return car.getId();
    }

    @Override
    public void updateCar(Long id, CarDTO carDto) throws CarNotFoundException {
        Optional<Car> opt = carDao.getById(id);
        if (opt.isPresent()) {
            Car car = opt.get();
            car.setModel(carDto.getModel());
            car.setBody(carDto.getBody());
            car.setType(carDto.getType());
            car.setEngineValue(carDto.getEngineValue());
            carDao.update(car);
        } else {
            throw new CarNotFoundException();
        }
    }

    @Override
    public void delete(Long id) {
        carDao.delete(id);
    }

    private CarImage getImage(MultipartFile file) throws IOException {
        byte[] photoBytes = file.getBytes();
        final String base64carImage = Base64.getEncoder().encodeToString(photoBytes);
        /**
         * file.getOriginalFileName() - C:/photos/photo.jpg
         * file.getOriginalFilename().substring(indexOfPoint + 1) == ".jpg"
         * file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(File.separator) + 1, indexOfPoint) == "photo"
         */
        final int indexOfPoint = file.getOriginalFilename().lastIndexOf(".");
        final String format = file.getOriginalFilename().substring(indexOfPoint + 1);
        final String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(File.separator) + 1, indexOfPoint);
        CarImage img = new CarImage();
        img.setFileName(fileName);
        img.setFormat(format);
        img.setPhoto(base64carImage);
        return img;
    }
}
