package com.sda.weather.weather;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class WeatherDto {

    private Long id;
    private float temperature;
    private int pressure;
    private int humidity;
    private int windSpeed;
    private int windDirection;
    private String date;


}
