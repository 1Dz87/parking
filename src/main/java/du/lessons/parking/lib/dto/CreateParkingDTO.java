package du.lessons.parking.lib.dto;

import java.util.Date;

public class CreateParkingDTO {

    private String parkingName;

    private Date createDate;

    private String north;
    private String east;
    private String west;
    private String south;

    private Integer eastPlaces;
    private Integer westPlaces;
    private Integer southPlaces;
    private Integer northPlaces;

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public Integer getEastPlaces() {
        return eastPlaces;
    }

    public void setEastPlaces(Integer eastPlaces) {
        this.eastPlaces = eastPlaces;
    }

    public Integer getWestPlaces() {
        return westPlaces;
    }

    public void setWestPlaces(Integer westPlaces) {
        this.westPlaces = westPlaces;
    }

    public Integer getSouthPlaces() {
        return southPlaces;
    }

    public void setSouthPlaces(Integer southPlaces) {
        this.southPlaces = southPlaces;
    }

    public Integer getNorthPlaces() {
        return northPlaces;
    }

    public void setNorthPlaces(Integer northPlaces) {
        this.northPlaces = northPlaces;
    }
}
