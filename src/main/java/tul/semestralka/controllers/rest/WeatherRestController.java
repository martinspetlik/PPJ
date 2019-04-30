package tul.semestralka.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tul.semestralka.service.MongoWeatherService;
import tul.semestralka.service.TownService;


@RestController
public class WeatherRestController {

    @Autowired
    MongoWeatherService weatherService;

    @Autowired
    TownService townService;

    final String WEATHERS_PATH = "/api/weathers";
    final String WEATHER_PATH = "/api/weather";
    final String ACT_WEATHER_PATH = "/api/actual-weather";
    final String AVG_WEATHER_PATH = "/api/average-weather";

}
