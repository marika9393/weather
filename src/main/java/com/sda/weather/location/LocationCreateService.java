package com.sda.weather.location;

public class LocationCreateService {

     public Location createLocation(String cityName, String countryName, String region, String longitude, String latitude) {
         Location location = new Location();
         location.setCityName(cityName);
         location.setCountryName(countryName);
         location.setRegion(region);
         location.setLongitude(longitude);
         location.setLatitude(latitude);
         return location;
    }
}
