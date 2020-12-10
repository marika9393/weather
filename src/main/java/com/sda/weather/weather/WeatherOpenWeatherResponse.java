package com.sda.weather.weather;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherOpenWeatherResponse {

    private String cod;
    private CityResponse city;
    private List<SingleWeather> list;;

    @Data
    public static class CityResponse {
        private String name;

    }

    @Data
    public static class SingleWeather {
        @JsonProperty("dt_txt")
        private String date;
        private Main main;
        private Wind wind;
    }

    @Data
    public static class Main {
        private String pressure;
        private String humidity;
        @JsonProperty("temp")
        private String temperature;
    }

    @Data
    public static class Wind {
        @JsonProperty("speed")
        private String windSpeed;
        @JsonProperty("deg")
        private String windDirection;
    }
}
