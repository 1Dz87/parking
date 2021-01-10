package du.lessons.parking.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "spring_user")
@NamedQueries(value = {
        @NamedQuery(name = User.GET_USER_BY_LOGIN, query = "SELECT u FROM User u where u.login=:login")
})
public class User {

    public static final String GET_USER_BY_LOGIN = "get.by.login";

    private Long id;

    private String login;

    private String firstName;

    private String lastName;

    private Integer age;

    private List<Car> cars;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "login", unique = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Car> getCars() {
        return cars;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
