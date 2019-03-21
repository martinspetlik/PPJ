package tul.semestralka;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tul.semestralka.data.Country;
import tul.semestralka.data.CountryDao;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountryDaoTests {

    @Autowired
    private CountryDao countryDao;

    @Test
    public void testUsers() {

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