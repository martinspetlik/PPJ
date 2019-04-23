package tul.semestralka.controllers.rest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tul.semestralka.data.Town;

@ConditionalOnProperty(value = "readOnlyMode", matchIfMissing=true, havingValue="false")
@RestController
public class TownWriteRestController extends TownRestController{

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
        Town changedTown = townService.getTown(town.getId());
        if (changedTown == null) {
            Message m = new Message("Town does not exist");
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }
        townService.saveOrUpdate(town);
        changedTown = townService.getTown(town.getId());
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
