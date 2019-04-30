package tul.semestralka.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tul.semestralka.data.Country;

import java.util.List;

@RestController
public class CountryReadController extends CountryRestController {
    @GetMapping(value = COUNTRIES_PATH)
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.getAllCountries();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping(value = COUNTRY_PATH + "/{code}")
    public ResponseEntity<Object> getCountry(@PathVariable("code") String code) {
        Country country = countryService.getCountry(code);
        if (country != null) {
            return new ResponseEntity<>(country, HttpStatus.OK);
        } else {
            Message er = new Message("Country not found by code: " + code);
            return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
        }
    }
}
