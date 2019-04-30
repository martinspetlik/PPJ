package tul.semestralka.controllers.rest;

import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tul.semestralka.data.Weather;

import javax.validation.Valid;

@ConditionalOnProperty(value = "readOnlyMode", matchIfMissing = true, havingValue = "false")
@RestController
public class WeatherWriteController extends WeatherRestController {
    @PostMapping(value = WEATHER_PATH)
    public ResponseEntity<Object> createWeather(@RequestBody Weather weather) {
        if (weatherService.exists(weather)) {
            Message m = new Message("Weather already exists: " + weather);
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        } else {
            if (townService.getTownById(weather.getTownId()) == null) {
                Message m = new Message("Town does not exist, town id: " + weather.getTownId());
                return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
            }
            weatherService.update(weather);
            return new ResponseEntity<>(weather, HttpStatus.OK);
        }
    }

    @PutMapping(value = WEATHER_PATH)
    public ResponseEntity<Object> updateWeather(@Valid @RequestBody Weather weather) {
        if (townService.getTownById(weather.getTownId()) == null) {
            Message m = new Message("Weather does not exist, invalid town id: " + weather.getTownId());
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }

        weatherService.update(weather);
        Weather changedWeather = weatherService.find(weather.getId());
        if (changedWeather != null) {
            Message m = new Message("Changed weather:" + changedWeather);
            return new ResponseEntity<>(m, HttpStatus.OK);
        } else {
            Message m = new Message("Failed to change weather:");
            return new ResponseEntity<>(m, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = WEATHER_PATH + "/{id}")
    public ResponseEntity<Object> deleteWeather(@PathVariable("id") ObjectId id) {

        Weather weather = weatherService.find(id);
        if (weatherService.exists(weather)) {
            weatherService.remove(weather);

            if (!weatherService.exists(weather)) {
                Message m = new Message("Weather was deleted: " + weather);
                return new ResponseEntity<>(m, HttpStatus.OK);
            } else {
                Message m = new Message("Failed to delete weather: " + weather);
                return new ResponseEntity<>(m, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Message m = new Message("Weather doesn't exist: " + weather);
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }
    }
}
