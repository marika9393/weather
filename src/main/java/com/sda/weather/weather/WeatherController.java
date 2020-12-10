package com.sda.weather.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@Validated
class WeatherController {

    final WeatherMapper.WeatherForecastService weatherCreateService;
    final WeatherMapper weatherMapper;

    @GetMapping("/location/{id}/weather")
    WeatherDto getWeather(@PathVariable Long id, @RequestParam(required = false, defaultValue = "1") @Min(1) @Max(5) Integer period) {
        Weather weather = weatherCreateService.getWeather(id, period);
        return weatherMapper.mapToWeatherDto(weather);
    }


    @GetMapping("/weather")
    String getWeather(@RequestParam String cityName, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        return null;
    }

}
