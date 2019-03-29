package tul.semestralka;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tul.semestralka.data.Country;
import tul.semestralka.data.CountryDao;
import tul.semestralka.data.Town;
import tul.semestralka.data.TownDao;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan("tul.semestralka.data")
public class Main {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Bean
    public SessionFactory sessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Bean
    public TownDao townDao() {
        return new TownDao();
    }

    @Bean
    public CountryDao countryDao() {
        return new CountryDao();
    }


    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        CountryDao countryDao = ctx.getBean(CountryDao.class);

        Country country = new Country("test", 17);
        countryDao.create(country);

        TownDao townDao = ctx.getBean(TownDao.class);

        Town town = new Town("test", country);
        townDao.create(town);

        List<Country> countries = countryDao.getAllCountries();
        System.out.println(countries);

    }

}

