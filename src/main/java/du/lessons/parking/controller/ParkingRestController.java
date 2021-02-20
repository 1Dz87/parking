package du.lessons.parking.controller;

import du.lessons.parking.lib.dto.CarDTO;
import du.lessons.parking.lib.dto.RegisterFormDTO;
import du.lessons.parking.lib.dto.UserDTO;
import du.lessons.parking.lib.exceptions.CarNotFoundException;
import du.lessons.parking.lib.exceptions.UserAlreadyExistsException;
import du.lessons.parking.lib.exceptions.UserNotFoundException;
import du.lessons.parking.model.User;
import du.lessons.parking.service.ICarService;
import du.lessons.parking.service.IUserService;
import du.lessons.parking.service.auth.IJwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(ParkingRestController.API)
public class ParkingRestController {

    static final String API = "/api";

    private final IUserService userService;

    private final ICarService carService;

    private final AuthenticationManager authenticationManager;

    private final IJwtProvider provider;

    @Autowired
    public ParkingRestController(IUserService userService, ICarService carService, AuthenticationManager authenticationManager, IJwtProvider provider) {
        this.userService = userService;
        this.carService = carService;
        this.authenticationManager = authenticationManager;
        this.provider = provider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.findUserByLogin(userDTO.getLogin(), false);
            if (user.isEnabled()) { //TODO: && user access rights
                Authentication auth = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(userDTO.getLogin(), userDTO.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(auth);
                String jwt = provider.generateJwtToken(user.getLogin());
                return ResponseEntity.ok(jwt);
            } else {
                return new ResponseEntity<>("Ваш профиль заблокирован", HttpStatus.UNAUTHORIZED);
            }
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("Не верные данные авторизации", HttpStatus.UNAUTHORIZED);
        }
    }

    // http://localhost:8080/api/user/test3 - реквест с переменной адреса
    @GetMapping(value = "/user/{login}")
    public ResponseEntity<User> user(@PathVariable String login) {
        // 1. при отсутствии параметра возвращается ошибка 404
        try {
            User user = userService.findUserByLogin(login, false);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     // http://localhost:8080/api/user?login=test3 - реквест с параметром
     @GetMapping(value = "/user")
     public ResponseEntity<User> userWithParam(@RequestParam String login) {
     // 1. при отсутствии параметра возвращается ошибка 500
     // 2. параметр может быть не обязательным
     }
     */

    @PostMapping(value = "/user/create")
    public ResponseEntity<?> create(@RequestBody RegisterFormDTO user) {
        try {
            User u = userService.createUser(user);
            return ResponseEntity.ok(u.getId());
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/user/{login}/addCar")
    public ResponseEntity<?> addCar(@PathVariable String login, @RequestBody Map<String, Object> carVo) {
        try {
            User user = userService.findUserByLogin(login, true);
            Long carId = carService.addUserCar(user, carVo);
            return ResponseEntity.ok(carId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(value = "/car/{id}/update")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @RequestBody CarDTO carDto) {
        try {
            carService.updateCar(id, carDto);
            return ResponseEntity.ok().build();
        } catch (CarNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/car/{id}/delete")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.ok().build();
    }

}
