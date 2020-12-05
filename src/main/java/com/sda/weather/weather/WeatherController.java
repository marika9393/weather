package com.sda.weather.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class WeatherController {


    private final WeatherService weatherCreateService;

    @GetMapping("/location/{id}/weather")
    WeatherDto getWeather(@PathVariable Long id, @RequestParam(required = false) String period) {
        Weather weather = weatherCreateService.getWeather(id, period);
        return null;
    }

//    @GetMapping("/weather")
//    String getWeather(@RequestParam String cityName, @RequestParam(required = false) String date) {
//        weatherCreateService.getWeatherByCityName(cityName, date);
//        return null;
//    }

}
