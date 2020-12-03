package com.sda.weather.weather;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class WeatherController {

    private WeatherCreateService weatherCreateService;


    @GetMapping("/weather")
    public ResponseEntity<WeatherDto> getWeather(@RequestParam String location) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(weatherCreateService.getWeather(location));
    }
}
