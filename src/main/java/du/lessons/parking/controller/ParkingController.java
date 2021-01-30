package du.lessons.parking.controller;

import du.lessons.parking.model.CarBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parking")
public class ParkingController {


    public void asd() {
        smth();
    }

    public void smth() {
        for (CarBody bodyOpt : CarBody.values()) {
            CarBody value = bodyOpt;
            String text = bodyOpt.getDescription();
        }
    }
}
