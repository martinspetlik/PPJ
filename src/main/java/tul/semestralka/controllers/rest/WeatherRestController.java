package tul.semestralka.controllers.rest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tul.semestralka.data.Weather;
import tul.semestralka.data.WeatherAverage;
import tul.semestralka.service.MongoWeatherService;
import tul.semestralka.service.TownService;
import javax.validation.Valid;
import java.util.List;


@RestController
public class WeatherRestController {

    @Autowired
    MongoWeatherService weatherService;

    @Autowired
    TownService townService;

    private final String WEATHERS_PATH = "/api/weathers";
    private final String WEATHER_PATH = "/api/weather";
    private final String ACT_WEATHER_PATH = "/api/actual-weather";
    private final String AVG_WEATHER_PATH = "/api/average-weather";


    @GetMapping(value = WEATHERS_PATH)
    public ResponseEntity<List<Weather>> getWeathers() {
        List<Weather> weathers = weatherService.getAll();
        return new ResponseEntity<>(weathers, HttpStatus.OK);
    }

    @GetMapping(value = WEATHER_PATH + "/{id}")
    public ResponseEntity<Object> getWeather(@PathVariable("id") ObjectId id) {
        Weather weather = weatherService.find(id);
        if (weather != null) {
            return new ResponseEntity<>(weather, HttpStatus.OK);
        } else {
            Message m = new Message("Weather not found by id: " + id);
            return new ResponseEntity<>(m, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = WEATHER_PATH)
    public ResponseEntity<Object> createWeather(@RequestBody Weather weather) {
        if (weatherService.exists(weather)) {
            Message m = new Message("Weather already exists: " + weather);
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        } else {
            if (townService.getTown(weather.getTownId()) == null) {
                Message m = new Message("Town does not exist, town id: " + weather.getTownId());
                return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
            }
            weatherService.add(weather);
            return new ResponseEntity<>(weather, HttpStatus.OK);
        }
    }

    @PutMapping(value = WEATHER_PATH)
    public ResponseEntity<Object> updateWeather(@Valid @RequestBody Weather weather) {
        if (townService.getTown(weather.getTownId()) == null) {
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

    @GetMapping(value = ACT_WEATHER_PATH + "/{townId}")
    public ResponseEntity<Object> actualWeather(@PathVariable("townId") Integer townId) {

        if (weatherService.existsByTownId(townId)) {
            Weather weather = weatherService.getActual(townId);

            if (weatherService.exists(weather)) {
                return new ResponseEntity<>(weather, HttpStatus.OK);
            } else {
                Message m = new Message("Failed to find weather: " + weather);
                return new ResponseEntity<>(m, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Message m = new Message("No weather for townId: " + townId);
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = AVG_WEATHER_PATH + "/{townId}")
    public ResponseEntity<Object> averageWeather(@PathVariable("townId") Integer townId, @RequestParam(value = "period") String period) {

        if (!period.equals("day")  && !period.equals("week") && !period.equals("fortnight")) {
            Message m = new Message("Period must be: day or week or fortnight");
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }

        if (weatherService.existsByTownId(townId)) {
            WeatherAverage weatherAvg = weatherService.getAverage(townId, period);

            if (weatherAvg == null) {
                Message m = new Message("No weather for given period: " + period);
                return new ResponseEntity<>(m, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(weatherAvg, HttpStatus.OK);
            }
        } else {
            Message m = new Message("No weather for townId: \" + townId: " + townId);
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }
    }


}
