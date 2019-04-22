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
    private MongoWeatherService mongoWeatherService;

    public void create(Town town) {
        if (!exists(town)) {
            townRepository.save(town);
        }
    }

    public List<Town> getTowns() {
        return StreamSupport.stream(townRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void saveOrUpdate(Town town) {
        townRepository.save(town);
    }

    public boolean exists(Town town) {
        if (town == null)
        {
            return false;
        }
        if (town.getCountry() != null) {
            return townRepository.existsByName(town.getName()) && townRepository.existsByCountry_Code(town.getCountry().getCode());
        } else {
            return townRepository.existsByName(town.getName());
        }
    }

    public void delete(Town town) {
        townRepository.delete(town);
    }

    public void deleteTowns() {
        townRepository.deleteAll();
    }

    public Town getTown(Integer id) {
        return townRepository.findById(id).orElse(null);
    }

    public List<Town> getTownsByCountry(Country country) {

        if (country == null) {
            return null;
        }

        List<Town> towns = townRepository.findByCountry(country);

        if (towns.size() == 0) {
            return null;
        }

        return towns;
    }

    public List<Town> getTownsByName(String name) {

        if (name == null) {
            return null;
        }

        List<Town> towns = townRepository.findByName(name);

        if (towns.size() == 0) {
            return null;
        }

        return towns;
    }

    public List<Town> getTownsByCountryCode(String code, boolean addLastWeather) {

        if (code.isEmpty()) {
            return null;
        }

        List<Town> towns = townRepository.getByCountryCode(code);

        if (towns.size() == 0) {
            return null;
        }


        // All lists with weather
        ArrayList<Town> townsWithWeather = new ArrayList<Town>();

        // Add weather to town
        for (Town town : towns)
        {
            if (addLastWeather) {
                // Actual weather
                Weather townWeathers = mongoWeatherService.getActual(town.getId());

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
