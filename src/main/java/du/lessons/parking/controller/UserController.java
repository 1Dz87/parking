package du.lessons.parking.controller;

import du.lessons.parking.model.User;
import du.lessons.parking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
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
}
