package com.sda.weather.weather.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.weather.Weather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceClient {

    private final RestTemplate restTemplate;
    private final  ObjectMapper objectMapper;
    private final WeatherClientProperties weatherClientProperties;

    public Optional<Weather> getWeather(String cityName, LocalDate weatherDate) {

        String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("api.openweathermap.org/data/2.5/forecast")
                .queryParam("q", cityName)
                .queryParam("units", "metric")
                .queryParam("appid", weatherClientProperties.getApiKey())
                .build()
                .toUriString();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
            String body = response.getBody();

            if(response.getStatusCode().isError()) {
                log.error("Connection error with url: " + url +", returned status code: " + response.getStatusCode().value());
                return Optional.empty();
            }
            WeatherOpenResponse weatherResponse = objectMapper.readValue(body, WeatherOpenResponse.class);
            LocalDateTime predictionDate = weatherDate.atTime(12, 00);
            return weatherResponse.getSingleWeatherList()
                    .stream()
                    .filter(f-> mapToLocalDateTime(f.getDate()).isEqual(predictionDate))
                    .map(this::mapToForecast)
                    .findFirst();
        } catch (JsonProcessingException e ) {
            log.error("Weather forecast information cannot be deserialized", e);
            return Optional.empty();
        } catch (RestClientException e) {
            log.error("Connection error with url" + url,e);
            return Optional.empty();
        }

    }

    private Weather mapToForecast(WeatherOpenResponse.SingleWeather singleForecast) {
        LocalDateTime forecastDate = mapToLocalDateTime(singleForecast.getDate());
        Instant forecastDateInstant = forecastDate.atZone(ZoneId.systemDefault()).toInstant();

        Weather forecast = new Weather();
        forecast.setHumidity(singleForecast.getMain().getHumidity());
        forecast.setPressure(singleForecast.getMain().getPressure());
        forecast.setTemperature(singleForecast.getMain().getTemp());
        forecast.setWindDirection((int) singleForecast.getWind().getDeg());
        forecast.setWindSpeed((int) singleForecast.getWind().getSpeed());
        forecast.setWeatherDate(forecastDateInstant);
        return forecast;
    }

    private LocalDateTime mapToLocalDateTime(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

}
