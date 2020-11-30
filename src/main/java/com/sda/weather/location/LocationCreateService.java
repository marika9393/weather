package com.sda.weather.location;

import com.sda.weather.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class LocationCreateService {

    final LocationRepository locationRepository;

    Location createLocation(LocationDefinition locationDefinition) {
        String cityName = locationDefinition.getCityName();
        String countryName = locationDefinition.getCountryName();
        Double longitude = locationDefinition.getLongitude();
        Double latitude = locationDefinition.getLatitude();

        if (cityName.isEmpty() || countryName.isEmpty() || cityName.isBlank() || countryName.isBlank()) {
            throw new BadRequestException("City or Country can't be empty");
        }

        if (longitude < -180 || longitude > 180 || latitude < -90 || latitude > 90) {
            throw new BadRequestException("Incorrect geographical coordinates.");
        }

        Location location = Location.builder()
                .cityName(cityName)
                .countryName(countryName)
                .longitude(longitude)
                .latitude(latitude)
                .build();

        String region = locationDefinition.getRegion();
        Optional.ofNullable(region)
                .filter(r -> !r.isBlank())
                .ifPresent(location::setRegion);

        return locationRepository.save(location);
    }
}
