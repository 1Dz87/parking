package du.lessons.parking.service;

import du.lessons.parking.lib.exceptions.RequiredFieldException;
import du.lessons.parking.model.Parking;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

public interface IParkingService {

    Parking createParking(String parkingName, Date createDate, String north, String east, String west, String south, Integer ePlaces,
                          Integer nPlaces, Integer wPlaces, Integer sPlaces) throws RequiredFieldException;

    Parking getById(Long id) throws Throwable;
}
