package tul.semestralka;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tul.semestralka.data.Town;
import tul.semestralka.data.Country;
import tul.semestralka.service.CountryService;
import tul.semestralka.service.TownService;
import java.util.List;

@SpringBootApplication
public class Main {

    @Bean
    public TownService townService() {return new TownService();}

    @Bean
    public CountryService countryService() {return new CountryService();}

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext context = app.run(args);

        CountryService countryService = context.getBean(CountryService.class);

        List<Country> countries = countryService.getAllCountries();
        System.out.println(countries);

    }
}


