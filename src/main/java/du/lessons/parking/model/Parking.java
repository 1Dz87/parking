package du.lessons.parking.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "parking")
public class Parking {

    private Long id;

    private String name;

    private Date createDate;

    private List<ParkingArea> parkingAreas;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    public List<ParkingArea> getParkingAreas() {
        if (parkingAreas == null) {
            parkingAreas = new ArrayList<>();
        }
        return parkingAreas;
    }

    public void setParkingAreas(List<ParkingArea> parkingAreas) {
        this.parkingAreas = parkingAreas;
    }

    @Temporal(value = TemporalType.DATE)
    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
