package tul.semestralka;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tul.semestralka.data.Weather;
import tul.semestralka.service.MongoWeatherService;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class WeatherTests {

    private MongoWeatherService weatherService;

    @Autowired
    private MongoTemplate mongo;

    @Bean
    public MongoWeatherService weatherService() {
        return new MongoWeatherService(mongo);
    }

    private Weather weather1 = new Weather(1, (float) 280.5, 1050, 57);
    private Weather weather2 = new Weather(1, (float) 285.5, 950, 59);
    private Weather weather3 = new Weather(1, (float) 300.7, 1108, 75);
    private Weather weather4 = new Weather(1, (float) 112.75, 1250, 85);


    @Before
    public void init() {
        weatherService = new MongoWeatherService(mongo);
        weatherService.removeAll();
    }

    @Test
    public void testCreateRetrieve() {
        weatherService.add(weather1);

        List<Weather> weathers1 = weatherService.getAll();
        assertEquals("One weather should have been created and retrieved", 1, weathers1.size());
        assertEquals("Inserted weather should match retrieved", weather1.getId(), weathers1.get(0).getId());

        weatherService.add(weather2);
        weatherService.add(weather3);
        weatherService.add(weather4);

        List<Weather> weather2 = weatherService.getAll();

        assertEquals("Should be four retrieved weathers.", 4, weather2.size());
    }
}