package com.sda.weather.weather;

import com.sda.weather.exception.InternalServerException;

public class WindDirectionMapper {

    private static final String[] Directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};

    String windDirectionMapp(int windDirection) {
        if (windDirection > 360 || windDirection < 0) {
            throw new InternalServerException("Incorrect wind value " + windDirection);
        }
        double value = Math.floor((windDirection / 22.5) + 0.5);
        return Directions[(int) (value % 16)];
    }
}
