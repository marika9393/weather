package com.sda.weather.location;

import com.sda.weather.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationCreateServiceTest {

    @Mock
    LocationRepository locationRepository;
    @InjectMocks
    LocationCreateService locationCreateService;


    @Test
    void createLocation_callsLocationRepository() {

        //given
        when(locationRepository.save(any(Location.class))).thenReturn(new Location());
        LocationDefinition location = LocationDefinition.builder()
                .cityName("Gdańsk")
                .countryName("Polska")
                .longitude(18.5)
                .latitude(54.4)
                .region("pomorskie")
                .build();

        //when
        Location result = locationCreateService.createLocation(location);

        //then
        assertThat(result).isExactlyInstanceOf(Location.class);
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void createLocation_whenCityIsEmpty_throwsException() {

        //given
        LocationDefinition location = LocationDefinition.builder()
                .cityName("")
                .countryName("Polska")
                .longitude(18.5)
                .latitude(54.4)
                .region("pomorskie")
                .build();

        //when
        Throwable result = catchThrowable(() -> locationCreateService.createLocation(location));

        //then
        assertThat(result).isInstanceOf(BadRequestException.class);
        verify(locationRepository, times(0)).save(any(Location.class));

    }

    @Test
    void createLocation_whenCityIsBlank_throwsException() {

        //given
        LocationDefinition location = LocationDefinition.builder()
                .cityName(" ")
                .countryName("Polska")
                .longitude(18.5)
                .latitude(54.4)
                .region("pomorskie")
                .build();

        //when
        Throwable result = catchThrowable(() -> locationCreateService.createLocation(location));

        //then
        assertThat(result).isInstanceOf(BadRequestException.class);
        verify(locationRepository, times(0)).save(any(Location.class));

    }

    @Test
    void createLocation_whenCountryIsEmpty_throwsException() {

        //given
        LocationDefinition location = LocationDefinition.builder()
                .cityName("Gdańsk")
                .countryName("")
                .longitude(18.5)
                .latitude(54.4)
                .region("pomorskie")
                .build();

        //when
        Throwable result = catchThrowable(() -> locationCreateService.createLocation(location));

        //then
        assertThat(result).isInstanceOf(BadRequestException.class);
        verify(locationRepository, times(0)).save(any(Location.class));

    }

    @Test
    void createLocation_whenCountryIsBlank_throwsException() {

        //given
        LocationDefinition location = LocationDefinition.builder()
                .cityName("Gdańsk")
                .countryName(" ")
                .longitude(18.5)
                .latitude(54.4)
                .region("pomorskie")
                .build();

        //when
        Throwable result = catchThrowable(() -> locationCreateService.createLocation(location));

        //then
        assertThat(result).isInstanceOf(BadRequestException.class);
        verify(locationRepository, times(0)).save(any(Location.class));

    }

    @Test
    void createLocation_whenLongitudeIsOver180() {

        LocationDefinition location = LocationDefinition.builder()
                .cityName("Zakopane")
                .countryName("Polska")
                .longitude(181.0)
                .latitude(0.0)
                .region("podkarpackie")
                .build();

        // when
        Throwable result = catchThrowable(() -> locationCreateService.createLocation(location));

        //then
        assertThat(result).isInstanceOf(BadRequestException.class);
        verify(locationRepository, times(0)).save(any(Location.class));
    }

    @Test
    void createLocation_whenLongitudeIsBelow180Negative() {

        LocationDefinition location = LocationDefinition.builder()
                .cityName("Zakopane")
                .countryName("Polska")
                .longitude(-181.0)
                .latitude(0.0)
                .region("podkarpackie")
                .build();

        // when
        Throwable result = catchThrowable(() -> locationCreateService.createLocation(location));

        //then
        assertThat(result).isInstanceOf(BadRequestException.class);
        verify(locationRepository, times(0)).save(any(Location.class));
    }

    @Test
    void createLocation_whenLatitudeIsOver90() {

        LocationDefinition location = LocationDefinition.builder()
                .cityName("Zakopane")
                .countryName("Polska")
                .longitude(120.0)
                .latitude(92.0)
                .region("podkarpackie")
                .build();

        // when
        Throwable result = catchThrowable(() -> locationCreateService.createLocation(location));

        //then
        assertThat(result).isInstanceOf(BadRequestException.class);
        verify(locationRepository, times(0)).save(any(Location.class));
    }

    @Test
    void createLocation_whenLatitudeIsBelow90Negative() {

        LocationDefinition location = LocationDefinition.builder()
                .cityName("Zakopane")
                .countryName("Polska")
                .longitude(130.0)
                .latitude(-91.0)
                .region("podkarpackie")
                .build();

        // when
        Throwable result = catchThrowable(() -> locationCreateService.createLocation(location));

        //then
        assertThat(result).isInstanceOf(BadRequestException.class);
        verify(locationRepository, times(0)).save(any(Location.class));
    }


}