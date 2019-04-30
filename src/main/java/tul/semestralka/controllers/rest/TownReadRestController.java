package tul.semestralka.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tul.semestralka.data.Town;

import java.util.List;

@RestController
public class TownReadRestController extends TownRestController {

    @GetMapping(value = TOWNS_PATH)
    public ResponseEntity<List<Town>> getTowns() {
        List<Town> towns = townService.getTowns();
        return new ResponseEntity<>(towns, HttpStatus.OK);
    }

    @GetMapping(value = TOWN_PATH + "/{townId}")
    public ResponseEntity<Object> getTown(@PathVariable("townId") Integer townId) {
        Town town = townService.getTownById(townId);
        if (town != null) {
            return new ResponseEntity<>(town, HttpStatus.OK);
        } else {
            Message er = new Message("Town not found by id: " + townId);
            return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
        }
    }
}
