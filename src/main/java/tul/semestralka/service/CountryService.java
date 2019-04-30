package tul.semestralka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tul.semestralka.data.Country;
import tul.semestralka.repositories.CountryRepository;
import tul.semestralka.repositories.TownRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private TownRepository townRepository;

    public void create(Country country) {
        if (country.getCode() == null) {
            country.generateCode();
        }
        countryRepository.save(country);
    }

    public boolean exists(Country country) {
        return (countryRepository.existsByTitle(country.getTitle()) || countryRepository.existsByCode(country.getCode()));
    }

    public List<Country> getAllCountries() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Country getCountry(String code) {
        return countryRepository.findById(code).orElse(null);
    }

    public void deleteCountries() {
        countryRepository.deleteAll();
    }

    public void deleteCountry(Country country) {
        countryRepository.delete(country);
    }

    public List<Country> getCountriesWithTown() {
        return StreamSupport.stream(townRepository.getCountriesWithTown().spliterator(), false).collect(Collectors.toList());
    }

    // exist code for country name
    public boolean existsCodeForCountryName(Country country) {
        String code = country.getCodeFromTitle(country.getTitle());
        return (!code.isEmpty() && code != null);
    }

    public void update(Country country) {
        countryRepository.save(country);
    }
}
