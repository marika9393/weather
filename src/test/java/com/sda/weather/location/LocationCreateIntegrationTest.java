package com.sda.weather.location;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
class LocationCreateIntegrationTest {



    @Autowired
    MockMvc mockMvc;
    @Autowired
    LocationRepository locationRepository;

    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void createLocation_createLocationReturn200SC() throws Exception {
        // given
        locationRepository.deleteAll();
        LocationDto locationDto = new LocationDto(null,
                "Gdansk",
                18.65,
                54.35,
                "pomorskie",
                "Polska");
        MockHttpServletRequestBuilder post = post("/location")
                .with(user("marika").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationDto));

        // when
        MvcResult result = mockMvc.perform(post).andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        LocationDto responseBody = objectMapper.readValue(response.getContentAsString(), LocationDto.class);
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getCityName()).isEqualTo("Gdansk");
    }


    @Test
    void createNewLocation_whenCountryIsEmpty_returnHttpStatus400Code() throws Exception {
        //given
        locationRepository.deleteAll();

        LocationDto locationDto = new LocationDto(null, "Gdansk", 18.65, 54.35, "pomorskie", "");

        String requestBody = objectMapper.writeValueAsString(locationDto);
        MockHttpServletRequestBuilder post = post("/location")
                .with(user("marika").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        //when
        MvcResult result = mockMvc.perform(post).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Location> locations = locationRepository.findAll();
        assertThat(locations).isEmpty();
    }


    @Test
    void createNewLocation_whenCityIsEmpty_returnHttpStatus400Code() throws Exception {
        //given
        locationRepository.deleteAll();

        LocationDto locationDto = new LocationDto(null,
                "",
                18.65,
                54.35,
                "pomorskie",
                "Polska");

        String requestBody = objectMapper.writeValueAsString(locationDto);
        MockHttpServletRequestBuilder post = post("/location")
                .with(user("marika").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        //when
        MvcResult result = mockMvc.perform(post).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Location> locations = locationRepository.findAll();
        assertThat(locations).isEmpty();
    }


    @Test
    void createNewLocation_whenLatitudeIsInvalid_returnHttpStatus400Code() throws Exception {
        //given
        locationRepository.deleteAll();

        LocationDto locationDto = new LocationDto(null,
                "Gdańsk",
                18.65,
                200.0,
                "pomorskie",
                "Polska");

        String requestBody = objectMapper.writeValueAsString(locationDto);
        MockHttpServletRequestBuilder post = post("/location")
                .with(user("marika").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        //when
        MvcResult result = mockMvc.perform(post).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Location> locations = locationRepository.findAll();
        assertThat(locations).isEmpty();
    }

    @Test
    void createNewLocation_whenLongitudeIsInvalid_returnHttpStatus400Code() throws Exception {
        //given
        locationRepository.deleteAll();

        LocationDto locationDto = new LocationDto(null,
                "Gdańsk",
                100.0,
                170.0,
                "pomorskie",
                "Polska");

        String requestBody = objectMapper.writeValueAsString(locationDto);
        MockHttpServletRequestBuilder post = post("/location")
                .with(user("marika").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        //when
        MvcResult result = mockMvc.perform(post).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Location> locations = locationRepository.findAll();
        assertThat(locations).isEmpty();
    }
}
