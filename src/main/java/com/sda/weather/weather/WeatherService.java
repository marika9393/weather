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

import java.util.List;

@Component
@RequiredArgsConstructor
class WeatherService {

    private final LocationFetchService locationFetchService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    Weather getWeather(Long id, Integer period) {
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

            List<WeatherOpenWeatherResponse.SingleWeather> singleWeathers = weather.getList(); // todo here we have a list of the weather forecast (40 entries for 5 days)
            // todo we have to choose 1 forecast for the selected day out of 40 possible
            // todo for each day there are 8 weather forecasts (every 3 hours), let's assume we want for 12:00
            // todo I propose to use stream and filter -> singleWeathers.stream().filter(...).findFirst().orElseThrow(...);  - use period

            // todo then we have to convert SingleWeather to Weather
            // todo then we have to set a Location for the Weather (relation one location - many Weathers)
            // todo then we have to save the Weather to the database
            // todo then we have to return saved Weather
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Weather();
    }

}
