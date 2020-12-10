package com.sda.weather.weather;

import com.sda.weather.exception.NotFoundException;
import com.sda.weather.location.Location;
import com.sda.weather.location.LocationFetchService;
import com.sda.weather.weather.client.WeatherServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;

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

    @Component
    @RequiredArgsConstructor
    static
    class WeatherForecastService {

        private final LocationFetchService locationFetchService;
        private final WeatherServiceClient weatherServiceClient;
        private final WeatherRepository weatherRepository;

        Weather getWeather(Long id, Integer period) {
            Location location = locationFetchService.fetchLocationById(id);
            String cityName = location.getCityName();
            LocalDate weatherDate = LocalDate.now().plusDays(period);

            Weather weather = weatherServiceClient
                    .getWeather(cityName, weatherDate)
                    .orElseThrow(() -> new NotFoundException("The weather forecast for " + cityName + " cannot be determineted for " + weatherDate));

            weather.setCreateDate(Instant.now());
            weather.setLocation(location);

            return weatherRepository.save(weather);
        }
    }
}
