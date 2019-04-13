package tul.semestralka.service;

import org.springframework.stereotype.Service;
import tul.semestralka.data.Town;
import tul.semestralka.data.Country;
import org.springframework.beans.factory.annotation.Autowired;
import tul.semestralka.repositories.TownRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class TownService {

    @Autowired
    private TownRepository townRepository;

    public void create(Town town) {
        townRepository.save(town);
    }

    public List<Town> getTowns() {
        return StreamSupport.stream(townRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void saveOrUpdate(Town town) {
        townRepository.save(town);
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
}
