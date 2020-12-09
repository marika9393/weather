package com.sda.weather.weather;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.weather.location.Location;
import com.sda.weather.location.LocationFetchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    LocationFetchService locationFetchService;
    @InjectMocks
    WeatherService weatherService;
    @Spy
    @Autowired
    ObjectMapper objectMapper;
    @Spy
    RestTemplate restTemplate;

    @Test
    void getWeather_returnCorrectWeather() {    // todo remove this test for the moment, we are using the integration test because we have a lot of dependencies now
        //given
        Location location = new Location();
        location.setCityName("Warsaw");
        when(locationFetchService.fetchLocationById(any())).thenReturn(location);

        //when
        weatherService.getWeather(1L, 4);

    }
}
