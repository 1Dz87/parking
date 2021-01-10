package du.lessons.parking.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_area")
public class ParkingArea {

    private Long id;

    private List<ParkingPlace> places;

    private ParkingAreaSide side;

    private Parking parking;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "parkingArea", cascade = CascadeType.ALL)
    public List<ParkingPlace> getPlaces() {
        return places;
    }

    public void setPlaces(List<ParkingPlace> places) {
        this.places = places;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "side")
    public ParkingAreaSide getSide() {
        return side;
    }

    public void setSide(ParkingAreaSide side) {
        this.side = side;
    }

    @ManyToOne
    @JoinColumn(name = "parking_id")
    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
