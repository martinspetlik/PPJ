package tul.semestralka;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tul.semestralka.api.data.WeatherApi;
import tul.semestralka.api.service.DownloadWeatherService;
import tul.semestralka.data.Country;
import tul.semestralka.data.Town;
import tul.semestralka.data.Weather;
import tul.semestralka.service.MongoWeatherService;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class WeatherUpdateTest {

    @Autowired
    private DownloadWeatherService weatherUpdateService;

    @Autowired
    public MongoWeatherService weatherService;

    @Test
    public void testApi() {

        Country country1 = new Country("Czech Republic", "cz");
        Town town1 = new Town("Prague", country1);
        RateLimiter rt = RateLimiter.create(1);
        WeatherApi received = weatherUpdateService.updateWeather(town1, rt);
        List<Weather> weathers =  weatherService.getAll();
        assertNotNull("Should not be null", received);
        assertNotEquals("Should not min float value", 0f, received.getMainWeather().getTemp());
        assertEquals(1, weathers.size());
    }
}
