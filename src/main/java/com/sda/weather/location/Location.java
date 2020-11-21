package com.sda.weather.location;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // can't be null
    private String cityName;
    private String longitude;
    private String latitude;
    private String region;
    // can't be null
    private String countryName;
}
