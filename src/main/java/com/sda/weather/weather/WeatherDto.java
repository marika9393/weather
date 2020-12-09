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
    private String temperature;
    private String pressure;
    private String humidity;
    private String windSpeed;
    private String windDirection;
}
