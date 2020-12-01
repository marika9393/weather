package com.sda.weather.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class LocationDefinition {

    private String cityName;
    private Double longitude;
    private Double latitude;
    private String region;
    private String countryName;
}
