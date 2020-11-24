package com.sda.weather.location;

import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    LocationDto mapToLocationDto(Location newLocation) {
        LocationDto locationDto = LocationDto.builder()
                .id(newLocation.getId())
                .cityName(newLocation.getCityName())
                .countryName(newLocation.getCountryName())
                .latitude(newLocation.getLatitude())
                .longitude(newLocation.getLongitude())
                .region(newLocation.getRegion().orElse(null))
                .build();

        return locationDto;
    }

    LocationDefinition mapToLocationDefinition(LocationDto locationDto) {
        LocationDefinition locationDefinition = LocationDefinition.builder()
                .cityName(locationDto.getCityName())
                .countryName(locationDto.getCountryName())
                .latitude(locationDto.getLatitude())
                .longitude(locationDto.getLongitude())
                .region(locationDto.getRegion())
                .build();

        return locationDefinition;
    }
}
