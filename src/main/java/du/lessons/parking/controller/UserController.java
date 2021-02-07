package du.lessons.parking.controller;

import du.lessons.parking.lib.dto.CommonResponse;
import du.lessons.parking.lib.exceptions.CarNotFoundException;
import du.lessons.parking.lib.util.Utils;
import du.lessons.parking.model.Car;
import du.lessons.parking.model.CarBody;
import du.lessons.parking.model.EngineType;
import du.lessons.parking.model.User;
import du.lessons.parking.service.ICarService;
import du.lessons.parking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
@SessionAttributes(value = {UserController.USER})
public class UserController {

    static final String USER = "user";

    private final IUserService userService;

    private final ICarService carService;

    @Autowired
    public UserController(IUserService userService, ICarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    @GetMapping("/userPage")
    public ModelAndView userPage(Principal principal, ModelAndView view) {
        String login = ((User)(((UsernamePasswordAuthenticationToken) principal).getPrincipal())).getLogin();
        if (login != null) {
            try {
                User user = userService.findUserByLogin(login, Boolean.TRUE);
                view.setViewName("userPage");
                view.addObject(USER, user);
            } catch (Exception e) {
                view.addObject("error", e.getMessage());
                view.setViewName("login");
            }
        } else {
            view.setViewName("login");
        }
        return view;
    }

    @ResponseBody
    @PostMapping("/addCar")
    public CommonResponse<Car> addCar(@RequestParam String model, @RequestParam CarBody body, @RequestParam EngineType engineType,
                                     @RequestParam Float engineValue, @RequestParam(required = false) MultipartFile carImage, @SessionAttribute(USER) User user) {
        Car car = null;
        try {
            car = carService.createCar(user, model, body, engineType, engineValue, carImage);
            user.getCars().add(car);
        } catch (IOException e) {
            CommonResponse<Car> response = new CommonResponse<>();
            response.getError(e.getMessage());
            return response;
        }
        CommonResponse<Car> response = new CommonResponse<>();
        response.getSuccess(car, Utils.getEmptyString());
        return response;
    }

    @ResponseBody
    @PostMapping("/updateCar")
    public CommonResponse<Car> updateCar(@RequestParam Long id, @RequestParam String model, @RequestParam CarBody body, @RequestParam EngineType engineType,
                            @RequestParam(required = false) Float engineValue, @RequestParam(required = false) MultipartFile carImage, @SessionAttribute(USER) User user) {
        try {
            Car car = carService.updateCar(id, model, body, engineType, engineValue, carImage);
            CommonResponse<Car> response = new CommonResponse<>();
            response.getSuccess(car, Utils.getEmptyString());
            return response;
        } catch (CarNotFoundException | IOException e) {
            CommonResponse<Car> response = new CommonResponse<>();
            response.getError(e.getMessage());
            return response;
        }
    }

}
