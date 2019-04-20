package tul.semestralka;

import ch.qos.logback.core.status.InfoStatus;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
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

//    private Instant t1 = Instant.now();
//    private Instant t2 = t1.plusSeconds(100);
//    private Instant t3 = t1.plusSeconds(600);
//    private Instant t4 = t1.plusSeconds(2000);

//    private Date t1 = new Date();
//    private Date t2 = new Date(t1.getTime() + 10 * 1000);
//    private Date t3 = new Date(t1.getTime() + 20 * 1000);
//    private Date t4 = new Date(t1.getTime() + 15 * 1000);

    private ZonedDateTime t1 = ZonedDateTime.now();
    private ZonedDateTime t2 = ZonedDateTime.now();
    private ZonedDateTime t3 = ZonedDateTime.now();
    private ZonedDateTime t4 = ZonedDateTime.now();
//    private Date t2 = new Date(t1.getTime() + 10 * 1000);
//    private Date t3 = new Date(t1.getTime() + 20 * 1000);
//    private Date t4 = new Date(t1.getTime() + 15 * 1000);

    private Weather weather1 = new Weather(1, (float)280.5, (float)1050, (float)57, (float)10, (float)25.6, t1);
    private Weather weather2 = new Weather(1, (float)285.5, (float)950, (float)59, (float)8, (float)180.1, t2);
    private Weather weather3 = new Weather(1, (float)300.7, (float)1108, (float)75, (float)15.5, (float)125.6, t3);
    private Weather weather4 = new Weather(1, (float)112.75, (float)1250, (float)85, (float)7.8, (float)180, t4);


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

    @Test
    public void testLastWeather() {
        weatherService.add(weather1);
        weatherService.add(weather2);
        weatherService.add(weather3);
        weatherService.add(weather4);
        Weather latestWeather = weatherService.findByTownId(weather4.getTownId()).get(0);
        assertEquals(latestWeather.getTime(), weather1.getTime());
    }
}