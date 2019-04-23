package tul.semestralka.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tul.semestralka.service.CountryService;
import tul.semestralka.service.TownService;


@RestController
public class TownRestController {

    @Autowired
    CountryService countryService;

    @Autowired
    TownService townService;

    final String TOWNS_PATH = "/api/towns";
    final String TOWN_PATH = "/api/town";

}
