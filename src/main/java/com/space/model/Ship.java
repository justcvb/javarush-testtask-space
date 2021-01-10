package com.space.model;

import com.space.exception.ValidationException;
import javax.persistence.*;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "ship")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String planet;
    @Enumerated(EnumType.STRING)
    private ShipType shipType;
    private Date prodDate;
    private boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPlanet() {
        return planet;
    }
    public void setPlanet(String planet) {
        this.planet = planet;
    }
    public ShipType getShipType() {
        return shipType;
    }
    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }
    public Date getProdDate() {
        return prodDate;
    }
    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }
    public boolean getUsed() {
        return isUsed;
    }
    public void setUsed(boolean used) {
        isUsed = used;
    }
    public Double getSpeed() {
        return speed;
    }
    public void setSpeed(Double speed) {
        this.speed = speed;
    }
    public Integer getCrewSize() {
        return crewSize;
    }
    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void calcRating() {
        double k = isUsed ? 0.5 : 1.0;
        int year = prodDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
        double numerator = 80 * speed * k;
        double denominator = 3019 - year + 1;
        rating = (double) Math.round(numerator / denominator * 100) / 100;
    }

    public void merge(Ship ship) {
        if (ship.name != null) name = ship.name;
        if (ship.planet != null) planet = ship.planet;
        if (ship.shipType != null) shipType = ship.shipType;
        if (ship.prodDate != null) prodDate = ship.prodDate;
        if (ship.speed != null) speed = ship.speed;
        if (ship.crewSize != null) crewSize = ship.crewSize;
    }

    public void validate() {
        if (name == null || planet == null || shipType == null || prodDate == null || speed == null|| crewSize == null) {
            throw new ValidationException();
        }
        if (name.length() == 0 || name.length() > 50) {
            throw new ValidationException();
        }
        if (planet.length() == 0 || planet.length() > 50) {
            throw new ValidationException();
        }
        int year = prodDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
        if (year < 2800 || year > 3019) {
            throw new ValidationException();
        }
        if (speed < 0.01 || speed > 0.99) {
            throw new ValidationException();
        }
        if (crewSize < 1 || crewSize > 9999) {
            throw new ValidationException();
        }
    }

}
