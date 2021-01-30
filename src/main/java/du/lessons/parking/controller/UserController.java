package du.lessons.parking.controller;

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
                view.addObject("user", user);
            } catch (Exception e) {
                view.addObject("error", e.getMessage());
                view.setViewName("login");
            }
        } else {
            view.setViewName("login");
        }
        return view;
    }

    @PostMapping("/addCar")
    public String addCar(@RequestParam String model, @RequestParam CarBody body, @RequestParam EngineType engineType,
                         @RequestParam Float engineValue, @RequestParam MultipartFile carImage, @ModelAttribute(USER) User user) {
        Car car = null;
        try {
            car = carService.createCar(user, model, body, engineType, engineValue, carImage);
            user.getCars().add(car);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/user/userPage";
    }

}
