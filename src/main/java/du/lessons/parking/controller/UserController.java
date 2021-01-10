package du.lessons.parking.controller;

import du.lessons.parking.model.User;
import du.lessons.parking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/userPage")
    public ModelAndView createUser(@RequestParam String login, @RequestParam Integer age, ModelAndView view) {
        User user = userService.createUser(login, age);
        view.setViewName("userPage");
        view.addObject("user", user);
        return view;
    }
}
