package com.sda.weather.weather.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherOpenResponse {

    @JsonProperty("list")
    private List<SingleWeather> singleWeatherList;

    @Data
    static class SingleWeather {
        @JsonProperty("dt_txt")
        private String date;
        private Main main;
        private Wind wind;

        @Data
        static class Main {
            private float temp;
            private int pressure;
            private int humidity;
        }

        @Data
        static class Wind {
            private float speed;
            private float deg;
        }
    }
}
