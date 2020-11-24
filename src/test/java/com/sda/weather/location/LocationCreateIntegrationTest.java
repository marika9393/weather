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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
class LocationCreateIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    LocationRepository locationRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void fetchNewLocation_createNewLocationAndReturn201StatusCode() throws Exception {  // todo move to eg. LocationFetchIntegrationTest
        //given
        locationRepository.deleteAll();

        Location location = Location.builder()
                .id(null)
                .countryName("Polska")
                .cityName("Gdansk")
                .longitude(18.65)
                .latitude(54.35)
                .region("Pomorskie")
                .build();


        Location location2 = Location.builder()
                .id(null)
                .countryName("Polska")
                .cityName("Zakopane")
                .longitude(19.57)
                .latitude(49.18)
                .region("Malopolskie")
                .build();


        locationRepository.save(location);
        locationRepository.save(location2);

        MockHttpServletRequestBuilder builder = get("/location")
                .contentType(MediaType.APPLICATION_JSON);


        //when
        MvcResult result = mockMvc.perform(builder).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String responseBody = response.getContentAsString();
        List<LocationDto> locations = objectMapper.readValue(responseBody, new TypeReference<>() {
        });
        assertThat(locations).hasSize(2);
        assertThat(locations).anySatisfy(singleLocation -> {
            assertThat(singleLocation.getCityName()).isEqualTo("Gdansk");
            assertThat(singleLocation.getCountryName()).isEqualTo("Polska");
            assertThat(singleLocation.getLongitude()).isEqualTo(18.65);
            assertThat(singleLocation.getLatitude()).isEqualTo(54.35);
            assertThat(singleLocation.getRegion()).isEqualTo("Pomorskie");
        });
        assertThat(locations).anySatisfy(singleLocation -> {
            assertThat(singleLocation.getCityName()).isEqualTo("Zakopane");
            assertThat(singleLocation.getRegion()).isEqualTo("Malopolskie");
            assertThat(singleLocation.getLongitude()).isEqualTo(19.57);
            assertThat(singleLocation.getLatitude()).isEqualTo(49.18);
            assertThat(singleLocation.getRegion()).isEqualTo("Malopolskie");
        });
    }


    @Test
    void fetchNewLocation_whenRegionIsEmpty_createNewLocationAndReturn201StatusCode() throws Exception { // todo move to eg. LocationFetchIntegrationTest
        //given
        locationRepository.deleteAll();

        Location location = Location.builder()
                .id(null)
                .countryName("Polska")
                .cityName("Gdansk")
                .longitude(18.65)
                .latitude(54.35)
                .region("")
                .build();


        locationRepository.save(location);

        MockHttpServletRequestBuilder builder = get("/location")
                .contentType(MediaType.APPLICATION_JSON);


        //when
        MvcResult result = mockMvc.perform(builder).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String responseBody = response.getContentAsString();
        List<LocationDto> locations = objectMapper.readValue(responseBody, new TypeReference<>() {
        });
        assertThat(locations).hasSize(1);
        assertThat(locations).anySatisfy(singleLocation -> {
            assertThat(singleLocation.getCityName()).isEqualTo("Gdansk");
            assertThat(singleLocation.getRegion()).isEqualTo("");
            // todo more assertions
        });
    }

    // todo add a new test for the happy-path eg. createNewLocation_savesNewLocationAndReturnHttpStatus200Code

    @Test
    void createNewLocation_whenCountryIsEmpty_returnHttpStatus400Code() throws Exception {
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
