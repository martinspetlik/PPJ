package tul.semestralka.service;

import org.springframework.stereotype.Service;
import tul.semestralka.data.Town;
import tul.semestralka.data.Country;
import org.springframework.beans.factory.annotation.Autowired;
import tul.semestralka.data.Weather;
import tul.semestralka.repositories.TownRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class TownService {

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private MongoWeatherService weatherService;

    public void create(Town town) {
        if (!exists(town)) {
            townRepository.save(town);
        }
    }

    public List<Town> getTowns() {
        return StreamSupport.stream(townRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void update(Town town) {
        if (exists(town)) {
            townRepository.save(town);
        }
    }

    public boolean exists(Town town) {
        if (town == null) {
            return false;
        }
        if (town.getCountry() != null) {
            return townRepository.existsByName(town.getName()) && townRepository.existsByCountry_Code(town.getCountry().getCode());
        } else {
            return townRepository.existsByName(town.getName());
        }
    }

    public Town getTown(Town town) {
        return townRepository.getByNameAndCountry(town.getName(), town.getCountry());
    }

    public void delete(Town town) {
        townRepository.delete(town);
    }

    public void deleteTowns() {
        townRepository.deleteAll();
    }

    public Town getTownById(Integer id) {
        return townRepository.findById(id).orElse(null);
    }

    public List<Town> getTownsByCountry(Country country) {

        if (country == null) {
            throw new NullPointerException("Country is null");
        }

        List<Town> towns = townRepository.findByCountry(country);
        return towns;
    }

    public List<Town> getTownsByName(String name) {

        if (name == null) {
            throw new NullPointerException("Name is null");
        }

        List<Town> towns = townRepository.findByName(name);
        return towns;
    }

    public ArrayList<Town> getTownsByCountryCode(String code, boolean addLastWeather) {

        ArrayList<Town> towns = new ArrayList<Town>();

        if (code.isEmpty()) {
            return towns;
        }

        towns = (ArrayList) townRepository.getByCountryCode(code);

        if (towns.size() == 0) {
            return towns;
        }


        // All lists with weather
        ArrayList<Town> townsWithWeather = new ArrayList<Town>();

        // Add weather to town
        for (Town town : towns) {
            if (addLastWeather) {
                // Actual weather
                Weather townWeathers = weatherService.getActual(town.getId());

                if (townWeathers == null) {
                    continue;
                }

                town.setLastWeather(townWeathers);

                townsWithWeather.add(town);
            }
        }
        return townsWithWeather;
    }
}
