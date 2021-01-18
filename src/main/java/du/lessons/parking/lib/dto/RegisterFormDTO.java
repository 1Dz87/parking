package du.lessons.parking.lib.dto;

import du.lessons.parking.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterFormDTO {

    private static final String FIELD_SIZE_ERROR = "Не верная длина поля";

    private static final String FIELD_FORMAT_ERROR = "Не верный формат поля";

    private static final String FIELD_REQUIRED_ERROR = "Это поле обязательно для заполнения";

    @Size(max = 255, message = FIELD_SIZE_ERROR)
    private String firstName;

    @Size(max = 255, message = FIELD_SIZE_ERROR)
    private String lastName;

    @NotBlank(message = FIELD_REQUIRED_ERROR)
    @Size(min = 3, max = 255, message = FIELD_SIZE_ERROR)
    private String login;

    @Email(message = FIELD_FORMAT_ERROR)
    private String email;

    @NotBlank(message = FIELD_REQUIRED_ERROR)
    @Size(min = 5, max = 255, message = FIELD_SIZE_ERROR)
    private String password;

    @NotBlank(message = FIELD_REQUIRED_ERROR)
    @Size(min = 5, max = 255, message = FIELD_SIZE_ERROR)
    private String repeatPassword;

    public User convert(BCryptPasswordEncoder encoder) {
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setLogin(this.login);
        user.setEmail(this.email);
        user.setPassword(encoder.encode(this.getPassword()));
        return user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}