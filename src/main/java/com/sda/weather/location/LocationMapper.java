package com.sda.weather.location;

import org.springframework.stereotype.Component;

@Component
class LocationMapper {

    LocationDto mapToLocationDto(Location newLocation) {
        return LocationDto.builder()
                .id(newLocation.getId())
                .cityName(newLocation.getCityName())
                .countryName(newLocation.getCountryName())
                .latitude(newLocation.getLatitude())
                .longitude(newLocation.getLongitude())
                .region(newLocation.getRegion().orElse(null))
                .build();
    }

    LocationDefinition mapToLocationDefinition(LocationDto locationDto) {
        return LocationDefinition.builder()
                .cityName(locationDto.getCityName())
                .countryName(locationDto.getCountryName())
                .latitude(locationDto.getLatitude())
                .longitude(locationDto.getLongitude())
                .region(locationDto.getRegion())
                .build();
    }
}
