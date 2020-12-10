package com.sda.weather.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherMapper {

    WeatherDto mapToWeatherDto(Weather newWeather) {
        return WeatherDto.builder()
                .temperature(newWeather.getTemperature())
                .pressure(newWeather.getPressure())
                .humidity(newWeather.getHumidity())
                .windDirection(newWeather.getWindDirection())
                .windSpeed(newWeather.getWindSpeed())
                .date(newWeather.getWeatherDate().toString())
                .build();
    }
}
