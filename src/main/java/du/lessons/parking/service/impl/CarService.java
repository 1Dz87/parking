package du.lessons.parking.service.impl;

import du.lessons.parking.lib.exceptions.CarNotFoundException;
import du.lessons.parking.model.*;
import du.lessons.parking.repository.ICarDao;
import du.lessons.parking.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
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
    public void updateCar(Car car, String model, CarBody body, EngineType engineType, Float engineValue, MultipartFile carImage) throws IOException {
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
