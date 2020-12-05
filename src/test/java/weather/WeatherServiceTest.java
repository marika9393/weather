package weather;

import com.sda.weather.location.Location;
import com.sda.weather.location.LocationFetchService;
import com.sda.weather.weather.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    LocationFetchService locationFetchService;
    @InjectMocks
    WeatherService weatherService;

    @Test
    void getWeather_returnsCorrectWeather(){

        //given
        Location location = new Location();
        location.setCityName("Warsaw");
        when(locationFetchService.fetchLocationById(any())).thenReturn(location);

        //when
        weatherService.getWeather(1L, "4");

        //then
    }
}
