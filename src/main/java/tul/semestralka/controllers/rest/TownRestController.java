package tul.semestralka.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tul.semestralka.data.Town;
import tul.semestralka.service.CountryService;
import tul.semestralka.service.TownService;

import java.util.List;
@RestController
public class TownRestController {

    @Autowired
    CountryService countryService;

    @Autowired
    TownService townService;

    private final String TOWNS_PATH = "/api/towns";
    private final String TOWN_PATH = "/api/town";


    @GetMapping(value = TOWNS_PATH)
    public ResponseEntity<List<Town>> getTowns() {
        List<Town> towns = townService.getTowns();

        //System.out.println(towns);

        for (Town t1 : towns) {
            System.out.println(t1);
        }
        return new ResponseEntity<>(towns, HttpStatus.OK);
    }

    @GetMapping(value = TOWN_PATH + "/{townId}")
    public ResponseEntity<Object> getTown(@PathVariable("townId") Integer townId) {
        Town town = townService.getTown(townId);
        if (town != null) {
            return new ResponseEntity<>(town, HttpStatus.OK);
        } else {
            Message er = new Message("Town not found by id: " + townId);
            return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = TOWN_PATH)
    public ResponseEntity<Object> createTown(@RequestBody Town town) {

        if (town.getCountry() == null) {
            Message er = new Message("Town must contains country e.g. {'title': 'Czech Republic', 'code': 'cz'}: " + town);
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }

        if (townService.exists(town)) {
            Message er = new Message("Town already exists: " + town);
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        } else {
            if (town.getCountry() == null || !countryService.exists(town.getCountry())) {
                Message er = new Message("Not valid country, add this country first: " + town.getCountry());
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            } else {
                townService.create(town);
                if (townService.exists(town)) {
                    return new ResponseEntity<>(town, HttpStatus.OK);
                } else {
                    Message er = new Message("Failed to create town: " + town);
                    return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    @PutMapping(value = TOWN_PATH)
    public ResponseEntity<Object> updateTown(@RequestBody Town town) {
        townService.saveOrUpdate(town);
        Town changedTown = townService.getTown(town.getId());
        if (changedTown == null) {
            Message m = new Message("Unable to change town id");
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }

        Message m = new Message("Changed town:" + changedTown);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @DeleteMapping(value = TOWN_PATH + "/{townId}")
    public ResponseEntity<Object> deleteTown(@PathVariable("townId") Integer townId) {
        Town town = townService.getTown(townId);
        if (!townService.exists(town)) {
            Message er = new Message("Town doesn't exist: " + town);
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        } else {
            townService.delete(town);

            if (!townService.exists(town)) {
                Message er = new Message("Town deleted: " + town);
                return new ResponseEntity<>(er, HttpStatus.OK);
            } else {
                Message er = new Message("Failed to delete town: " + town);
                return new ResponseEntity<>(er, HttpStatus.OK);
            }

        }
    }
}
