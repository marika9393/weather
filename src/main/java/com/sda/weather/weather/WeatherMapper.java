package com.sda.weather.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WeatherMapper {

    WeatherDto mapToWeatherDto(Weather newWeather) {
        return WeatherDto.builder()
                .id(newWeather.getId())
                .temperature(newWeather.getTemperature())
                .pressure(newWeather.getPressure())
                .humidity(newWeather.getHumidity())
                .windDirection(newWeather.getWindDirection())
                .windSpeed(newWeather.getWindSpeed())
                .date(newWeather.getWeatherDate().toString())
                .build();
    }
}
