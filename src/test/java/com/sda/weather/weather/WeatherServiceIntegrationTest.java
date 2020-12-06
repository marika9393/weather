package com.sda.weather.weather;

import com.sda.weather.location.Location;
import com.sda.weather.location.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
class WeatherServiceIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    LocationRepository locationRepository;

    Location savedLocation;

    @BeforeEach
    void setUp() {
        locationRepository.deleteAll();
        Location location = new Location();
        location.setCityName("Warsaw");
        location.setCountryName("Poland");
        location.setLatitude(50.0);
        location.setLongitude(40.0);
        savedLocation = locationRepository.save(location);
    }

    @Test
    void getWeather_returnsCorrectWeatherAnd200statusCode() throws Exception {
        //given
        Long id = savedLocation.getId();

        MockHttpServletRequestBuilder request = get("/location/" + id + "/com/sda/weather/weather") // todo use correct url
                .contentType(MediaType.APPLICATION_JSON);

        //when
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void getWeather_whenPeriodIsOver5_returns400StatusCode() throws Exception {
        //given
        Long id = savedLocation.getId();
        MockHttpServletRequestBuilder request = get("/location/" + id + "/com/sda/weather/weather") // todo use correct url
                .contentType(MediaType.APPLICATION_JSON);

        //when
        MockHttpServletResponse response = mockMvc.perform(request).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
