package du.lessons.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@JsonIgnoreProperties(value = { "user" })
@NamedQueries({@NamedQuery(query = "delete from Car where id=:id", name = Car.DELETE_QUERY)})
public class Car {

    public static final String DELETE_QUERY = "delete.car";

    private Long id;

    private String model;

    private CarBody body;

    private Float engineValue;

    private EngineType type;

    private User user;

    private CarImage image;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "body")
    public CarBody getBody() {
        return body;
    }

    public void setBody(CarBody body) {
        this.body = body;
    }

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    public CarImage getImage() {
        return image;
    }

    public void setImage(CarImage image) {
        this.image = image;
    }

    @Column(name = "engine_value")
    public Float getEngineValue() {
        return engineValue;
    }

    public void setEngineValue(Float engineValue) {
        this.engineValue = engineValue;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "engine_type")
    public EngineType getType() {
        return type;
    }

    public void setType(EngineType type) {
        this.type = type;
    }
}
