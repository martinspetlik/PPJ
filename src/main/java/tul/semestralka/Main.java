package tul.semestralka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import tul.semestralka.data.Town;
import tul.semestralka.data.Country;
import tul.semestralka.data.Weather;
import tul.semestralka.service.CountryService;
import tul.semestralka.service.MongoWeatherService;
import tul.semestralka.service.TownService;
import tul.semestralka.service.WeatherService;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Main {

    @Autowired
    private MongoTemplate mongo;

    @Bean
    public MongoWeatherService weatherService() {
        return new MongoWeatherService(mongo);
    }


    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext context = app.run(args);

        CountryService countryService = context.getBean(CountryService.class);
        TownService townService = context.getBean(TownService.class);

        List<Country> countries = countryService.getAllCountries();
        System.out.println(countries);

        Country c1 = new Country("Slovakia", "sk");
        Town t1 = new Town("Bratislava", c1);
        countryService.create(c1);
        townService.create(t1);

        MongoWeatherService weatherService = context.getBean(MongoWeatherService.class);

        Weather w = new Weather(t1.getId(), (float) 20.5, (float)1.5, (float)110, (float)75,(float) 10, ZonedDateTime.now());

        weatherService.add(w);
        System.out.println(weatherService.findByTownId(w.getTownId()).get(0).getHumidity());

    }
}


