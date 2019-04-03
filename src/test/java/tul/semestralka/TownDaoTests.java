package tul.semestralka;

import tul.semestralka.data.Town;
import tul.semestralka.data.Country;
import tul.semestralka.service.TownService;
import tul.semestralka.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
@ActiveProfiles({"test"})
public class TownDaoTests {

    @Autowired
    private TownService townService;

    @Autowired
    private CountryService countryService;

    private Country country1 = new Country("zeme 1 ", 123);
    private Country country2 = new Country("zeme 2 ", 456);
    private Country country3 = new Country("zeme 3 ", 789);
    private Country country4 = new Country("zeme 4 ", 147);




    private Town town1 = new Town("mesto 1 ", country1);
    private Town town2 = new Town("mesto 2 ", country1);
    private Town town3 = new Town("mesto 3 ", country2);
    private Town town4 = new Town("mesto 4 ", country3);
    private Town town5 = new Town("mesto 5 ", country3);
    private Town town6 = new Town("mesto 5 ", country3);
    private Town town7 = new Town("mesto 5 ", country4);


    @Before
    public void init() {
        countryService.deleteCountries();
    }


    @Test
    public void testDelete() {
        countryService.create(country1);
        countryService.create(country2);
        countryService.create(country3);
        countryService.create(country4);
        townService.saveOrUpdate(town2);
        townService.saveOrUpdate(town3);
        townService.saveOrUpdate(town4);
        townService.saveOrUpdate(town5);
        townService.saveOrUpdate(town6);
        townService.saveOrUpdate(town7);

        Town retrieved1 = townService.getTown(town2.getId());
        assertNotNull("Town with ID " + retrieved1.getId() + " should not be null (deleted, actual)", retrieved1);

        townService.delete(town2.getId());

        Town retrieved2 = townService.getTown(town2.getId());
        assertNull("Town with ID " + retrieved1.getId() + " should be null (deleted, actual)", retrieved2);
    }

    @Test
    public void testGetById() {
        countryService.create(country1);
        countryService.create(country2);
        countryService.create(country3);
        countryService.create(country4);
        townService.saveOrUpdate(town1);
        townService.saveOrUpdate(town2);
        townService.saveOrUpdate(town3);
        townService.saveOrUpdate(town4);
        townService.saveOrUpdate(town5);
        townService.saveOrUpdate(town6);
        townService.saveOrUpdate(town7);

        Town retrieved1 = townService.getTown(town1.getId());
        assertEquals("Towns should match", town1, retrieved1);

    }

    @Test
    public void testCreateRetrieve() {
        countryService.create(country1);
        countryService.create(country2);
        countryService.create(country3);
        countryService.create(country4);

        townService.saveOrUpdate(town1);

        List<Town> towns1 = townService.getTowns();
        assertEquals("Should be one town.", 1, towns1.size());

        assertEquals("Retrieved town should equal inserted town.", town1,
                towns1.get(0));

        townService.saveOrUpdate(town2);
        townService.saveOrUpdate(town3);
        townService.saveOrUpdate(town4);
        townService.saveOrUpdate(town5);
        townService.saveOrUpdate(town6);
//        townService.saveOrUpdate(town7);

        List<Town> towns2 = townService.getTowns();
        assertEquals("Should be six towns for enabled countries.", 6,
                towns2.size());

    }

    @Test
    public void testUpdate() {
        countryService.create(country1);
        countryService.create(country2);
        countryService.create(country3);
        countryService.create(country4);
        townService.saveOrUpdate(town2);
        townService.saveOrUpdate(town3);
        townService.saveOrUpdate(town4);
        townService.saveOrUpdate(town5);
        townService.saveOrUpdate(town6);
        townService.saveOrUpdate(town7);

        town3.setName("New town name");
        townService.saveOrUpdate(town3);

        Town retrieved = townService.getTown(town3.getId());
        assertEquals("Retrieved town should be updated.", town3, retrieved);
    }

    @Test
    public void testGetTown() {
        countryService.create(country1);
        countryService.create(country2);
        countryService.create(country3);
        countryService.create(country4);

        townService.saveOrUpdate(town1);
        townService.saveOrUpdate(town2);
        townService.saveOrUpdate(town3);
        townService.saveOrUpdate(town4);
        townService.saveOrUpdate(town5);
        townService.saveOrUpdate(town6);
        townService.saveOrUpdate(town7);

        List<Town> towns1 = townService.getTownsByCountry(country2);
        assertEquals("Should be three towns for this town.", 1, towns1.size());

        List<Town> towns3 = townService.getTownsByName(town2.getName());
        assertEquals("Should be 1 town for this country.", 1, towns3.size());
    }


}
