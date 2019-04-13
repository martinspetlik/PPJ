package tul.semestralka.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import tul.semestralka.data.Weather;
import tul.semestralka.repositories.CountryRepository;


public interface WeatherService {

    Weather find(ObjectId objectId);
    Weather add(Weather weather);
    void remove(Weather weather);

}
