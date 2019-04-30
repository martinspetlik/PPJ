package tul.semestralka;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import tul.semestralka.data.Weather;
import tul.semestralka.data.WeatherAverage;
import tul.semestralka.service.MongoWeatherService;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class WeatherTests {

    @Autowired
    //@Qualifier("weather")
    public MongoWeatherService weatherService;

    private ZonedDateTime t1 = ZonedDateTime.now();
    private ZonedDateTime t2 = ZonedDateTime.now();
    private ZonedDateTime t3 = ZonedDateTime.now();
    private ZonedDateTime t4 = ZonedDateTime.now();

    private Weather weather1 = new Weather(1, (float) 11.7, (float) 1050, (float) 57, (float) 9.9, (float) 25.6, t1);
    private Weather weather2 = new Weather(1, (float) 20.3, (float) 950, (float) 59, (float) 8.1, (float) 179.3, t2);
    private Weather weather3 = new Weather(1, (float) 10, (float) 1108, (float) 76, (float) 15, (float) 125.1, t3);
    private Weather weather4 = new Weather(2, (float) 8.1, (float) 1250, (float) 85, (float) 7.8, (float) 180, t4);
    private Weather weather5 = new Weather(1, (float) 2, (float) 892, (float) 48, (float) 7, (float) 50, t3.minusDays(2));
    private Weather weather6 = new Weather(1, (float) 11, (float) 400, (float) 60, (float) 15, (float) 125, t3.minusDays(8));

    @Before
    public void init() {
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
        Weather latestWeather = weatherService.findByTownId(weather1.getTownId()).get(0);
        assertEquals(latestWeather.getTime(), weather1.getTime());
    }

    @Test
    public void TestAverageWeatherDay() {
        double delta = 0.0001;
        weatherService.add(weather1);
        weatherService.add(weather2);
        weatherService.add(weather3);
        weatherService.add(weather4);
        weatherService.add(weather5);
        weatherService.add(weather6);

        WeatherAverage avgWeather = weatherService.getAverage(1, MongoWeatherService.Period.valueOf("day"));
        assertEquals(14, avgWeather.getTempAvg(), delta);
        assertEquals(1036, avgWeather.getPressureAvg(), delta);
        assertEquals(64, avgWeather.getHumidityAvg(), delta);
        assertEquals(110, avgWeather.getWindDegreeAvg(), delta);
        assertEquals(11, avgWeather.getWindSpeedAvg(), delta);

        WeatherAverage avgWeather2 = weatherService.getAverage(2, MongoWeatherService.Period.valueOf("day"));
        assertEquals(8.1, avgWeather2.getTempAvg(), delta);
        assertEquals(1250, avgWeather2.getPressureAvg(), delta);
        assertEquals(85, avgWeather2.getHumidityAvg(), delta);
        assertEquals(180, avgWeather2.getWindDegreeAvg(), delta);
        assertEquals(7.8, avgWeather2.getWindSpeedAvg(), delta);
    }

    @Test
    public void TestAverageWeatherDayErr() {
        weatherService.add(weather5);
        weatherService.add(weather6);
        WeatherAverage avgWeather = weatherService.getAverage(1, MongoWeatherService.Period.valueOf("day"));
        assertEquals(null, avgWeather);
    }


    @Test
    public void TestAverageWeatherWeek() {
        double delta = 0.0001;
        weatherService.add(weather1);
        weatherService.add(weather2);
        weatherService.add(weather3);
        weatherService.add(weather4);
        weatherService.add(weather5);
        weatherService.add(weather6);

        WeatherAverage avgWeather = weatherService.getAverage(1, MongoWeatherService.Period.valueOf("week"));
        assertEquals(11, avgWeather.getTempAvg(), delta);
        assertEquals(1000, avgWeather.getPressureAvg(), delta);
        assertEquals(60, avgWeather.getHumidityAvg(), delta);
        assertEquals(95, avgWeather.getWindDegreeAvg(), delta);
        assertEquals(10, avgWeather.getWindSpeedAvg(), delta);

        WeatherAverage avgWeather2 = weatherService.getAverage(2, MongoWeatherService.Period.valueOf("week"));
        assertEquals(8.1, avgWeather2.getTempAvg(), delta);
        assertEquals(1250, avgWeather2.getPressureAvg(), delta);
        assertEquals(85, avgWeather2.getHumidityAvg(), delta);
        assertEquals(180, avgWeather2.getWindDegreeAvg(), delta);
        assertEquals(7.8, avgWeather2.getWindSpeedAvg(), delta);
    }

    @Test
    public void TestAverageWeatherFortnight() {
        double delta = 0.0001;
        weatherService.add(weather1);
        weatherService.add(weather2);
        weatherService.add(weather3);
        weatherService.add(weather4);
        weatherService.add(weather5);
        weatherService.add(weather6);

        WeatherAverage avgWeather = weatherService.getAverage(1, MongoWeatherService.Period.valueOf("fortnight"));
        assertEquals(11, avgWeather.getTempAvg(), delta);
        assertEquals(880, avgWeather.getPressureAvg(), delta);
        assertEquals(60, avgWeather.getHumidityAvg(), delta);
        assertEquals(101, avgWeather.getWindDegreeAvg(), delta);
        assertEquals(11, avgWeather.getWindSpeedAvg(), delta);


        WeatherAverage avgWeather2 = weatherService.getAverage(2, MongoWeatherService.Period.valueOf("fortnight"));
        assertEquals(8.1, avgWeather2.getTempAvg(), delta);
        assertEquals(1250, avgWeather2.getPressureAvg(), delta);
        assertEquals(85, avgWeather2.getHumidityAvg(), delta);
        assertEquals(180, avgWeather2.getWindDegreeAvg(), delta);
        assertEquals(7.8, avgWeather2.getWindSpeedAvg(), delta);
    }
}