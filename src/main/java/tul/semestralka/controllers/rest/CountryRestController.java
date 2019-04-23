package tul.semestralka.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tul.semestralka.service.CountryService;
import tul.semestralka.service.TownService;

@RestController
public class CountryRestController {

    @Autowired
    CountryService countryService;

    @Autowired
    TownService townService;

    final String COUNTRIES_PATH = "/api/countries";
    final String COUNTRY_PATH = "/api/country";
}
