package tul.semestralka;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tul.semestralka.data.Country;
import tul.semestralka.data.CountryDao;
import tul.semestralka.data.Town;
import tul.semestralka.data.TownDao;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"test"})
@SpringApplicationConfiguration(classes = {Main.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TownDaoTests {

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private TownDao townDao;


    @Test
    public void Test1_createTown() {

        Country country = new Country("This is a test country.", 123);

        assertTrue("Country creation should return true", countryDao.create(country));

        Town town = new Town("Jablonec", country);

        assertTrue("Town creation should return true", townDao.create(town));

    }

    @Test
    public void Test2_listTowns() {

        List<Town> towns = townDao.getTowns();
        // Get the town with ID filled in.
        Town town = towns.get(0);

        assertEquals("Should be one town in database.", 1, towns.size());

        assertEquals("Retrieved town should match created town.", town, towns.get(0));
    }

    @Test
    public void Test3_updateTown() {

        List<Town> towns = townDao.getTowns();

        // Get the town with ID filled in.
        Town town = towns.get(0);

        town.setName("test");
        assertTrue("Town update should return true", townDao.update(town));

        List<Town> towns_2 = townDao.getTowns();

        Town updated = townDao.getTown(town.getId());

        assertEquals("Updated town should match retrieved updated town", town, updated);
    }

    @Test
    public void Test4_getTownById() {

        List<Country> countries = countryDao.getAllCountries();

        System.out.printf("počet zemí " + countries.size() + "prvni země" + countries);

        // Test get by ID ///////
        Town town2 = new Town("This is a test town.", countries.get(0));

        assertTrue("Town creation should return true", townDao.create(town2));

        List<Town> towns = townDao.getTowns();
        
        assertEquals("Should be two towns.", 2, towns.size());

        List<Town> secondList = townDao.getTowns();
        System.out.println(secondList);

        for (Town current : secondList) {
            Town retrieved = townDao.getTown(current.getId());

            assertEquals("Town by ID should match town from list.", current, retrieved);
        }
    }

    @Test
    public void Test5_deleteTown() {

        List<Town> towns = townDao.getTowns();

        // Get the towns with ID filled in.
        Town town = towns.get(0);

        // Test deletion
        townDao.delete(town.getId());

        List<Town> finalList = townDao.getTowns();

        assertEquals("Town lists should contain one town.", 1, finalList.size());
    }

}

