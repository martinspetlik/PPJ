package tul.semestralka.api.service;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import tul.semestralka.api.data.WeatherApi;
import tul.semestralka.data.Town;
import tul.semestralka.data.Weather;
import tul.semestralka.service.MongoWeatherService;
import tul.semestralka.service.TownService;
import java.util.List;


public class DownloadWeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MongoWeatherService weatherService;

    @Autowired
    private TownService townService;

    @Value("${weather.api.url}")
    private String weatherApiUrl;
    @Value("${weather.api.key}")
    private String weatherApiKey;


    @Scheduled(fixedDelayString = "${download.period}")
    public void scheduled(){
        RateLimiter rateLimiter = RateLimiter.create(1);
        List<Town> allTowns = townService.getTowns();

        for (Town town : allTowns) {
            updateWeather(town, rateLimiter);
        }
    }

    public WeatherApi updateWeather(Town town, RateLimiter rateLimiter) {
        String restUrl = String.format(weatherApiUrl, town.getName(), town.getCountry().getCode(), weatherApiKey);
        WeatherApi response = null;

        try {
            response = restTemplate.exchange(restUrl, HttpMethod.GET, null, WeatherApi.class).getBody();
            saveWeather(response, town);
            rateLimiter.acquire();
        } catch (Exception e) {
            System.out.println("Update weather exception " + e);
        }

        return response;
    }

    private void saveWeather(WeatherApi weatherData, Town town)
    {
        Weather weather = new Weather(town.getId(), weatherData.getTemp(), weatherData.getHumidity(),
                weatherData.getPressure(), weatherData.getWindSpeed(), weatherData.getWindDegree());

        weatherService.add(weather);
    }
}
