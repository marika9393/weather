package com.sda.weather.location;

import com.sda.weather.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationFetchService {

    private final LocationRepository locationRepository;

    public Location fechLocation(Long id) {
        return locationRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException("Not found location: " + id));
    }
}
