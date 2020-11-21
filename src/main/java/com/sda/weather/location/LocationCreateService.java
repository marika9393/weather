package com.sda.weather.location;

import com.sda.weather.exception.BadRequestExeption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationCreateService {

    final LocationRepository locationRepository;

     public Location createLocation(String cityName, String countryName, String region, String longitude, String latitude) {

         if (cityName.isEmpty() || countryName.isEmpty()) {
             throw new BadRequestExeption("City or Country can't be empty");
         }

         if (region.isEmpty()) {
             throw new BadRequestExeption("Region can't be empty");
         }
//         if (){}

         Location location = new Location();
         location.setCityName(cityName);
         location.setCountryName(countryName);
         location.setRegion(region);
         location.setLongitude(longitude);
         location.setLatitude(latitude);
         return locationRepository.save(location);
    }
}
