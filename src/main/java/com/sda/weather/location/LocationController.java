package com.sda.weather.location;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class LocationController {

    final LocationCreateService locationCreateService;
    final LocationMapper locationMapper;
    final LocationFetchService locationFetchService;

    @GetMapping("/location/{id}")
    LocationDto getLocation(@PathVariable Long id) {

        Location location = locationFetchService.fechLocation(id);
        return locationMapper.mapToLocationDto(location);

    }

    @PostMapping("/location")
    ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {

        String countryName = locationDto.getCountryName();
        String cityName = locationDto.getCityName();
        String latitude = locationDto.getLatitude();
        String longitude = locationDto.getLongitude();
        String region = locationDto.getRegion();

        Location newLocation = locationCreateService.createLocation(countryName, cityName, latitude, longitude, region);
        log.info(newLocation);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationMapper.mapToLocationDto(newLocation));
    }


}
