package tul.semestralka;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tul.semestralka.data.Country;
import tul.semestralka.service.CountryService;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountryDaoTests {

    @Autowired
    private CountryService countryService;

    private Country country1 = new Country("Czech Republic", "cz");
    private Country country2 = new Country("Slovakia", "sk");
    private Country country3 = new Country("Hungary");
    private Country country4 = new Country("Poland");


    @Before
    public void init() {
        countryService.deleteCountries();
    }


    @Test
    public void testCreateRetrieve() {
        countryService.create(country1);

        List<Country> countries1 = countryService.getAllCountries();

        System.out.println(countries1);

        assertEquals("One country should have been created and retrieved", 1, countries1.size());

        assertEquals("Inserted country should match retrieved", country1, countries1.get(0));

        countryService.create(country2);
        countryService.create(country3);
        countryService.create(country4);

        List<Country> countries2 = countryService.getAllCountries();

        assertEquals("Should be four retrieved countries.", 4, countries2.size());
    }

    @Test
    public void testExists() {
        countryService.create(country1);
        countryService.create(country2);
        countryService.create(country3);

        assertTrue("Country should exist.", countryService.exists(country2));
        assertFalse("Country should not exist.", countryService.exists(country4));
    }
}
