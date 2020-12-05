package com.sda.weather.weather;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherOpenWeatherResponse {

    private String cod;
    private int cnt;
    private CityResponse city;
    private List<SingleWeather> list;

    @Data
    public static class CityResponse {
        private String name;

    }

    @Data
    public static class SingleWeather {
        @JsonProperty("dt_txt")
        private String date;
    }
}
