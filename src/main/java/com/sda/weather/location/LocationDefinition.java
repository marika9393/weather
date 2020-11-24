package com.sda.weather.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDefinition {

    private String cityName;
    private Double longitude;
    private Double latitude;
    private String region;
    private String countryName;



}
