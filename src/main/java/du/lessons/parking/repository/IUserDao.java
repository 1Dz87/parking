package du.lessons.parking.repository;


import du.lessons.parking.model.User;

import java.util.Optional;

public interface IUserDao {

    void createUser(User user);

    boolean ifUserExists(String login);

    User findById(Long name);

    User getCarOwner(String model);

    Optional<User> getUserByLogin(String login, boolean initLazyObjects);
}
