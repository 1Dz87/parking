package du.lessons.parking;

import du.lessons.parking.controller.AuthController;
import du.lessons.parking.lib.dto.RegisterFormDTO;
import du.lessons.parking.lib.exceptions.UserAlreadyExistsException;
import du.lessons.parking.model.User;
import du.lessons.parking.service.IUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class RegistrationTest {

    @Autowired
    private AuthController controller;

    @Mock
    private HttpServletRequest request;

    private static RegisterFormDTO registerFormDTO;

    @BeforeAll
    public static void setUp() {
        registerFormDTO = new RegisterFormDTO();
        registerFormDTO.setFirstName("test10");
        registerFormDTO.setLastName("test10");
        registerFormDTO.setLogin("test10");
        registerFormDTO.setEmail("test10@test10.test10");
        registerFormDTO.setPassword("test10");
        registerFormDTO.setRepeatPassword("test10");
    }

    @Test
    void registerTest() throws ServletException {
        ModelAndView view = new ModelAndView();
        doNothing().when(request).login(anyString(), anyString());
        controller.register(registerFormDTO, view, request);
        assertEquals("redirect:/user/userPage", view.getViewName());
        User user = (User) view.getModel().get("user");
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(registerFormDTO.getLogin(), user.getLogin());
        assertEquals(registerFormDTO.getFirstName(), user.getFirstName());
        assertEquals(registerFormDTO.getLastName(), user.getLastName());
        assertEquals(registerFormDTO.getEmail(), user.getEmail());
    }
}
