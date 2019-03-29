package tul.semestralka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tul.semestralka.data.Country;
import tul.semestralka.data.CountryDao;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
@ActiveProfiles({"test"})
public class CountryDaoTests {

    @Autowired
    private CountryDao countryDao;

    @Test
    public void testCountries() {

        countryDao.deleteCountries();

        Country country = new Country("test", 123);

        assertTrue("Country creation should return true", countryDao.create(country));

        List<Country> countries = countryDao.getAllCountries();

        assertEquals("Number of countries should be 1.", 1,countries.size());

        assertTrue("Country should exist.", countryDao.exists(country.getTitle()));

        assertEquals("Created country should be identical to retrieved country",
                country, countries.get(0));

    }
}