package com.sda.weather.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.exception.NotFoundException;
import com.sda.weather.location.Location;
import com.sda.weather.location.LocationFetchService;
import com.sda.weather.weather.client.WeatherServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
class WeatherService {

    private final LocationFetchService locationFetchService;
    private final WeatherServiceClient weatherServiceClient;
    private final WeatherRepository weatherRepository;

     Weather getWeather(Long id, Integer period) {

        Location location = locationFetchService.fetchLocationById(id);
        String cityName = location.getCityName();
        LocalDate weatherDate = LocalDate.now().plusDays(period);

        Weather weather = weatherServiceClient.getWeather(cityName,weatherDate)
                .isElseThrow(() -> new NotFoundException("The weather forecast for " + cityName + " cannot be determineted for " + weatherDate ));

        weather.setWeatherDate(Instant.now());
        weather.setLocation(location);

        return weatherRepository.save(weather);
    }

}
