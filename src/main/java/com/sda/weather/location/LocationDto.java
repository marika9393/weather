package com.sda.weather.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {

    private Long id;
    private String cityName;
    private Double longitude;
    private Double latitude;
    private String region;
    private String countryName;

}
