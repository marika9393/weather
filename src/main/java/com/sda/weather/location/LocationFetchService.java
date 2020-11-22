package com.sda.weather.location;

import com.sda.weather.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationFetchService {

    private final LocationRepository locationRepository;

    public Location fetchLocation(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found location: " + id));
    }
}
