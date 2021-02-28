package du.lessons.parking;

import du.lessons.parking.controller.UserController;
import du.lessons.parking.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserIntegrationTest {

    @Autowired
    private UserController controller;

    private static Stream<Arguments> userPageTest() {
        return Stream.of(
                Arguments.of(new UsernamePasswordAuthenticationToken(getUserPrincipal("test3"), null),
                        getExpectedModel("userPage", true)),
                Arguments.of(new UsernamePasswordAuthenticationToken(getUserPrincipal("rjetkureuie"), null),
                        getExpectedModel("login", false)),
                Arguments.of(new UsernamePasswordAuthenticationToken(getUserPrincipal(null), null),
                        getExpectedModel("login", false)));
    }

    private static Object getUserPrincipal(String login) {
        User principal = new User();
        principal.setLogin(login);
        return principal;
    }

    private static ModelAndView getExpectedModel(String viewName, boolean isUserExpected) {
        ModelAndView result = new ModelAndView(viewName);
        result.addObject("isUserExpected", isUserExpected);
        return result;
    }

    @ParameterizedTest
    @MethodSource
    void userPageTest(Principal principal, ModelAndView expected) {
        ModelAndView modelAndView = new ModelAndView();
        boolean isUserExpected = (boolean) expected.getModel().get("isUserExpected");
        controller.userPage(principal, modelAndView);
        assertEquals(expected.getViewName(), modelAndView.getViewName());
        if (isUserExpected) {
            assertNotNull(((User) modelAndView.getModel().get("user")).getId());
        } else {
            assertNull((modelAndView.getModel().get("user")));
        }
    }
}
