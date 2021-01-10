package du.lessons.parking.service.impl;

import du.lessons.parking.lib.exceptions.RequiredFieldException;
import du.lessons.parking.lib.util.Utils;
import du.lessons.parking.model.Parking;
import du.lessons.parking.model.ParkingArea;
import du.lessons.parking.model.ParkingAreaSide;
import du.lessons.parking.model.ParkingPlace;
import du.lessons.parking.repository.IParkingDAO;
import du.lessons.parking.service.IParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService implements IParkingService {

    private final IParkingDAO parkingDAO;

    @Autowired
    public ParkingService(IParkingDAO parkingDAO) {
        this.parkingDAO = parkingDAO;
    }

    @Override
    public Parking createParking(String parkingName, Date createDate, String north, String east, String west, String south,
                                 Integer ePlaces, Integer nPlaces, Integer wPlaces, Integer sPlaces) throws RequiredFieldException {
        Parking parking = new Parking();
        parking.setName(parkingName);
        parking.setCreateDate(createDate);

        if (Utils.checkboxToBoolean(north)) {
            ParkingArea nArea = createArea(ParkingAreaSide.NORTH, nPlaces, parking);
            parking.getParkingAreas().add(nArea);
        }
        if (Utils.checkboxToBoolean(east)) {
            ParkingArea eArea = createArea(ParkingAreaSide.EAST, ePlaces, parking);
            parking.getParkingAreas().add(eArea);
        }
        if (Utils.checkboxToBoolean(west)) {
            ParkingArea wArea = createArea(ParkingAreaSide.WEST, wPlaces, parking);
            parking.getParkingAreas().add(wArea);
        }
        if (Utils.checkboxToBoolean(west)) {
            ParkingArea sArea = createArea(ParkingAreaSide.SOUTH, sPlaces, parking);
            parking.getParkingAreas().add(sArea);
        }
        parkingDAO.save(parking);
        return parking;
    }

    @Override
    public Parking getById(Long id) throws Throwable {
        return parkingDAO.getById(id).orElseThrow(() ->new Exception("No parking found"));
    }

    private ParkingArea createArea(ParkingAreaSide side, Integer places, Parking parking) throws RequiredFieldException {
        ParkingArea area = new ParkingArea();
        area.setSide(side);
        area.setParking(parking);
        if (places != null && places > 0) {
            List<ParkingPlace> placesList = new ArrayList<>();
            for (int i = 0; i < places; i++) {
                ParkingPlace parkingPlace = new ParkingPlace();
                parkingPlace.setNumber(i + 1);
                parkingPlace.setParkingArea(area);
                placesList.add(parkingPlace);
            }
            area.setPlaces(placesList);
        } else {
            throw new RequiredFieldException("Не заполнено количество парковочных мест для парковочной зоны: " + side.getDisplayField());
        }
        return area;
    }
}
