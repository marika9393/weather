package com.sda.weather.weather;


import com.sda.weather.location.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Instant createDate;
    Instant weatherDate;
    float temperature;
    int pressure;
    int humidity;
    int windSpeed;
    int windDirection;
    @ManyToOne
    private Location location;

}
