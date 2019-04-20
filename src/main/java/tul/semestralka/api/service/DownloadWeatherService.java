package tul.semestralka.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tul.semestralka.api.data.WeatherApi;
import tul.semestralka.data.Town;
import tul.semestralka.data.Weather;
import tul.semestralka.service.WeatherService;

@Service
public class DownloadWeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherService weatherService;

    @Value("${weather.api.url}")
    private String weatherApiUrl;
    @Value("${weather.api.key}")
    private String weatherApiKey;

    public WeatherApi updateWeather(Town town) {
        String restUrl = String.format(weatherApiUrl, town.getName(), town.getCountry().getCode(), weatherApiKey);

        WeatherApi response = restTemplate.exchange(restUrl, HttpMethod.GET, null, WeatherApi.class).getBody();
        saveWeather(response, town);

        return response;
    }

    private void saveWeather(WeatherApi weatherData, Town town)
    {
        Weather weather = new Weather(town.getId(), weatherData.getTemp(), weatherData.getHumidity(),
                weatherData.getPressure(), weatherData.getWindSpeed(), weatherData.getWindDegree());

        weatherService.add(weather);
    }
}
