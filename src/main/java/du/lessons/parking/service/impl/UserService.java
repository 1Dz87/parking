package du.lessons.parking.service.impl;

import du.lessons.parking.lib.exceptions.UserNotFoundException;
import du.lessons.parking.model.User;
import du.lessons.parking.repository.IUserDao;
import du.lessons.parking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserDao userDAO;

    @Autowired
    public UserService(IUserDao userDAO) {
        this.userDAO = userDAO;
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

}
