package tul.semestralka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tul.semestralka.data.Country;
import tul.semestralka.repositories.CountryRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public void create(Country country) {
        countryRepository.save(country);
    }

    public boolean exists(String title) {
        return countryRepository.existsByTitle(title).size() > 0;
    }

    public List<Country> getAllCountries() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void deleteCountries() {
        countryRepository.deleteAll();
    }
}
