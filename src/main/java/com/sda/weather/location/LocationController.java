package com.sda.weather.location;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequiredArgsConstructor
public class LocationController {

    final LocationCreateService locationCreateService;
    final LocationMapper locationMapper;
    final LocationFetchService locationFetchService;

    // todo add new endpoint GET: /location

    @GetMapping("/location/{id}")
    LocationDto getLocation(@PathVariable Long id) {
        Location location = locationFetchService.fetchLocation(id);
        return locationMapper.mapToLocationDto(location);
    }

    @PostMapping("/location")
    ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        String countryName = locationDto.getCountryName();
        String cityName = locationDto.getCityName();
        String latitude = locationDto.getLatitude();
        String longitude = locationDto.getLongitude();
        String region = locationDto.getRegion();
        // todo LocationDefinition
        Location newLocation = locationCreateService.createLocation(countryName, cityName, latitude, longitude, region);
        // todo more details in the message
        log.info(newLocation);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationMapper.mapToLocationDto(newLocation));
    }


}
