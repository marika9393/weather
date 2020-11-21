package com.sda.weather.location;

import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
     LocationDto mapToLocationDto(Location newLocation) {
        LocationDto locationDto = new LocationDto();
        locationDto.setCityName(newLocation.getCityName());
        locationDto.setCountryName(newLocation.getCountryName());
        locationDto.setLongitude(newLocation.getLongitude());
        locationDto.setLatitude(newLocation.getLatitude());
        locationDto.setRegion(newLocation.getRegion());
        return locationDto;
    }
}
