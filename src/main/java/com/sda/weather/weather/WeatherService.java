package com.sda.weather.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.location.Location;
import com.sda.weather.location.LocationFetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class WeatherService {

    private final LocationFetchService locationFetchService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public Weather getWeather(Long id, Integer period) {

        Location location = locationFetchService.fetchLocationById(id);
        String cityName = location.getCityName();

        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("api.openweathermap.org/data/2.5/forecast")
                .queryParam("q", cityName)
                .queryParam("appid", "bc55be57a274617383c51d005b4b3486")
                .build()
                .toUriString();


        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        String response = entity.getBody();


        try {
            WeatherOpenWeatherResponse weather = objectMapper.readValue(response, WeatherOpenWeatherResponse.class);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Weather();
    }

}
