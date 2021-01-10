package du.lessons.parking.service;


import du.lessons.parking.lib.exceptions.UserNotFoundException;
import du.lessons.parking.model.User;

public interface IUserService {

    User findUserByLogin(String login, boolean initLazyObjects) throws UserNotFoundException;

    User createUser(String login, Integer age);
}
