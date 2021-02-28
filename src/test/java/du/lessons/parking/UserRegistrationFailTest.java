package du.lessons.parking;

import du.lessons.parking.controller.AuthController;
import du.lessons.parking.lib.dto.RegisterFormDTO;
import du.lessons.parking.lib.exceptions.UserAlreadyExistsException;
import du.lessons.parking.lib.util.Utils;
import du.lessons.parking.model.User;
import du.lessons.parking.repository.IUserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRegistrationFailTest {

    @Autowired
    private AuthController controller;

    @Autowired
    private IUserDao dao;

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Mock
    private HttpServletRequest request;

    private static RegisterFormDTO registerFormDTO;

    private static User user;

    @BeforeAll
    public static void setUp() {
        registerFormDTO = new RegisterFormDTO();
        registerFormDTO.setFirstName("test10");
        registerFormDTO.setLastName("test10");
        registerFormDTO.setLogin("test10");
        registerFormDTO.setEmail("test10@test10.test10");
        registerFormDTO.setPassword("test10");
        registerFormDTO.setRepeatPassword("test10");
        user = registerFormDTO.convert(encoder);
    }

    @Test
    public void convertTestFails() {
        dao.createUser(user);
        ModelAndView view = new ModelAndView();
        controller.register(registerFormDTO, view, request);
        assertEquals("register", view.getViewName());
        assertNotNull(view.getModel().get("loginError"));
        assertEquals("Пользователь с таким именем уже существует", view.getModel().get("loginError"));
    }
}
