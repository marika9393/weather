package com.sda.weather.location;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class LocationController {

    @GetMapping("/location")
    List<LocationDto> getLocation() {
        LocationDto locationDto = new LocationDto();
        locationDto.setCityName("");
        locationDto.setCountryName("");
        locationDto.setLatitude("");
        locationDto.setLongitude("");
        locationDto.setRegion("");
        return Collections.singletonList(locationDto);
    }

    @PostMapping("/location")
    LocationDto createLocation(@RequestBody LocationDto locationDto) {

        LocationCreateService locationCreateService = new LocationCreateService();

        String countryName = locationDto.getCountryName();
        String cityName = locationDto.getCityName();
        String latitude = locationDto.getLatitude();
        String longitude = locationDto.getLongitude();
        String region = locationDto.getRegion();
        Location newLocation = locationCreateService.createLocation(countryName, cityName, latitude, longitude,region);
        log.info(newLocation);

        return mapToLocationDto(newLocation);
    }

    private LocationDto mapToLocationDto(Location newLocation) {
        LocationDto locationDto = new LocationDto();
        locationDto.setCityName(newLocation.getCityName());
        locationDto.setCountryName(newLocation.getCountryName());
        locationDto.setLongitude(newLocation.getLongitude());
        locationDto.setLatitude(newLocation.getLatitude());
        locationDto.setRegion(newLocation.getRegion());
        return locationDto;
    }
}
