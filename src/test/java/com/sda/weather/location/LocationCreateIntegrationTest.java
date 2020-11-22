package com.sda.weather.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
public class LocationCreateIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    LocationRepository locationRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createNewLocation_createNewLocationAndReturn200StatusCode() throws Exception {

        //given
        locationRepository.deleteAll();
        LocationDto locationDto = new LocationDto(null, "Gdansk", 18.65, 54.35, "pomorskie", "Polska");
        String requestBody = objectMapper.writeValueAsString(locationDto);
        MockHttpServletRequestBuilder post = post("/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        //when
        MvcResult result = mockMvc.perform(post).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        LocationDto location = objectMapper.readValue(response.getContentAsString(), LocationDto.class);
        assertThat(location.getId()).isNotNull();
        assertThat(location.getCityName()).isEqualTo("Gdansk");

    }

    @Test
    void createNewLocation_whnCountryIsEmpty_returnHttpStatus400Code() throws Exception {

        //given
        locationRepository.deleteAll();
        LocationDto locationDto = new LocationDto(null, "Gdansk", 18.65, 54.35, "pomorskie", "");
        String requestBody = objectMapper.writeValueAsString(locationDto);
        MockHttpServletRequestBuilder post = post("/location")
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
