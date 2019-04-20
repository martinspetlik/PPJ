package tul.semestralka.service;

import org.bson.types.ObjectId;
import tul.semestralka.data.Weather;


public interface WeatherService {

    Weather find(ObjectId objectId);
    Weather add(Weather weather);
    void remove(Weather weather);
    void update(Weather weather);
}
