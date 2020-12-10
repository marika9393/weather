package com.sda.weather.weather;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDto {

    Long id;
    float temperature;
    int pressure;
    int humidity;
    int windSpeed;
    int windDirection;
    String date;



}
