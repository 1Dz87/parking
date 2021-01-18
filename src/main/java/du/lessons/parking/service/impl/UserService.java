package du.lessons.parking.service.impl;

import du.lessons.parking.lib.dto.RegisterFormDTO;
import du.lessons.parking.lib.exceptions.UserAlreadyExistsException;
import du.lessons.parking.lib.exceptions.UserNotFoundException;
import du.lessons.parking.model.User;
import du.lessons.parking.repository.IUserDao;
import du.lessons.parking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final IUserDao userDAO;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(IUserDao userDAO, BCryptPasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.encoder = encoder;
    }

    @Override
    public User findUserByLogin(String login, boolean initLazyObjects) throws UserNotFoundException {
        Optional<User> opt = userDAO.getUserByLogin(login, initLazyObjects);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new UserNotFoundException("Пользователь с логином " + login + " не найден");
        }
    }

    @Override
    public User createUser(String login, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setAge(age);
        userDAO.createUser(user);
        return user;
    }

    @Override
    public User createUser(RegisterFormDTO registerForm) throws UserAlreadyExistsException {
        User user = registerForm.convert(encoder);
        if (userDAO.ifUserExists(user.getLogin())) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        userDAO.createUser(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDAO.getUserByLogin(s, false)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + s + " не найден"));
    }
}
