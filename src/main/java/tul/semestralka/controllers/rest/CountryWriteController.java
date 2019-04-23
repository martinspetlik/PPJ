package tul.semestralka.controllers.rest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tul.semestralka.data.Country;
import tul.semestralka.data.Town;

import java.util.List;

@ConditionalOnProperty(value = "readOnlyMode", matchIfMissing=true, havingValue="false")
@RestController
public class CountryWriteController extends CountryRestController{
    @PostMapping(value = COUNTRY_PATH)
    public ResponseEntity<Object> createCountry(@RequestBody Country country) {

        if (countryService.exists(country)) {
            Message er = new Message("Country already exists: " + country);
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        } else {
            if (!countryService.validData(country)) {
                Message er = new Message("Not valid data for country: " + country);
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);

            } else {
                countryService.create(country);
                if (countryService.exists(country)) {
                    return new ResponseEntity<>(country, HttpStatus.OK);
                } else {
                    Message er = new Message("Country doesn't exist: " + country);
                    return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
    }


    @PutMapping(value = COUNTRY_PATH)
    public ResponseEntity<Object> updateCountry(@RequestBody Country country) {
        countryService.update(country);
        Country changedCountry = countryService.getCountry(country.getCode());

        Message m = new Message("Changed country:" + changedCountry);
        return new ResponseEntity<>(m, HttpStatus.OK);

    }

    @DeleteMapping(value = COUNTRY_PATH + "/{code}")
    public ResponseEntity<Object> deleteCountry(@PathVariable("code") String code) {
        Country country = countryService.getCountry(code);
        if (!countryService.exists(country)) {
            Message er = new Message("Country doesn't exist: " + country);
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }

        List<Town> towns = townService.getTownsByCountry(country);
        if (towns != null && towns.size() > 0) {
            Message er = new Message("Country contains towns delete them first: " + towns);
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }

        countryService.deleteCountry(country);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
