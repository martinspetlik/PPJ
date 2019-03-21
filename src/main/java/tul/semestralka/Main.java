package tul.semestralka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import tul.semestralka.data.Country;
import tul.semestralka.data.CountryDao;
import tul.semestralka.data.TownDao;
import tul.semestralka.provisioning.Provisioner;

import java.util.List;

@SpringBootApplication
public class Main {

    @Bean
    public TownDao townDao() {
        return new TownDao();
    }

    @Bean
    public CountryDao countryDao() {
        return new CountryDao();
    }

    @Profile({"devel", "test"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        CountryDao countryDao = ctx.getBean(CountryDao.class);

        Country country = new Country("test", 1235674);
        countryDao.create(country);

        List<Country> countries = countryDao.getAllCountries();
        System.out.println(countries);

        //SpringApplication.run(Main.class, args);


    }

}
