package du.lessons.parking.controller;

import du.lessons.parking.lib.dto.RegisterFormDTO;
import du.lessons.parking.model.User;
import du.lessons.parking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class AuthController {

    private final IUserService userService;

    @Autowired
    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView registerForm(ModelAndView view) {
        view.setViewName("register");
        view.addObject("registerForm", new RegisterFormDTO());
        return view;
    }

    @PostMapping("/registration")
    public ModelAndView register(@ModelAttribute("registerForm") @Valid RegisterFormDTO registerForm,
                                 BindingResult bindingResult, ModelAndView view) {
        if (bindingResult.hasErrors()) {
            view.setViewName("register");
            return view;
        }
        if (!registerForm.getPassword().equals(registerForm.getRepeatPassword())) {
            view.setViewName("register");
            view.addObject("passwordError", "Пароли не совпадают");
            return view;
        }
        try {
            User user = userService.createUser(registerForm);
            view.setViewName("/user/userPage");
            view.addObject("user", user);
        } catch (Exception e) {
            view.setViewName("register");
            view.addObject("loginError", e.getMessage());
        }
        return view;
    }
}
