package tul.semestralka.controllers.rest;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tul.semestralka.data.Weather;
import tul.semestralka.data.WeatherAverage;
import tul.semestralka.service.MongoWeatherService;

import java.util.List;

@RestController
public class WeatherReadController extends WeatherRestController {


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

        try {
            if (weatherService.existsByTownId(townId)) {
                WeatherAverage weatherAvg = weatherService.getAverage(townId, MongoWeatherService.Period.valueOf("day"));

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
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
