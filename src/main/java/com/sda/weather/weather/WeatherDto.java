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
    String temperature;
    String presure;
    String humidity;
    String windSpeed;
    String windDirection;



}
