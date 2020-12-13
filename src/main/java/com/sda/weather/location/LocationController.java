package com.sda.weather.location;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
class LocationController {

    final LocationCreateService locationCreateService;
    final LocationMapper locationMapper;
    final LocationFetchService locationFetchService;

    @GetMapping("/location")
    List<Location> getAllLocation() {
        return locationFetchService.fetchAllLocation();
    }

    @GetMapping("/location/{id}")
    LocationDto getLocationById(@PathVariable Long id) {
        Location location = locationFetchService.fetchLocationById(id);
        return locationMapper.mapToLocationDto(location);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/location")
    ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        LocationDefinition locationDefinition = locationMapper.mapToLocationDefinition(locationDto);
        Location newLocation = locationCreateService.createLocation(locationDefinition);
        log.info("create new location: " + newLocation);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationMapper.mapToLocationDto(newLocation));
    }
}
