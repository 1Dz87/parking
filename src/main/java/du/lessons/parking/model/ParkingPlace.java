package du.lessons.parking.model;

import javax.persistence.*;

@Entity
@Table(name = "parking_place")
public class ParkingPlace {

    private Long id;

    private Integer number;

    private boolean occupied = false;

    private ParkingArea parkingArea;

    private Car occupiedCar;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column(name = "occupied")
    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @ManyToOne
    @JoinColumn(name = "parking_area_id")
    public ParkingArea getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }

    @Transient
    public Car getOccupiedCar() {
        return occupiedCar;
    }

    public void setOccupiedCar(Car occupiedCar) {
        this.occupiedCar = occupiedCar;
    }
}
