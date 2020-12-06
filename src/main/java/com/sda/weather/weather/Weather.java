package com.sda.weather.weather;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
     String temperature;
     String pressure;
     String humidity;
     String windSpeed;
     String windDirection;

}
