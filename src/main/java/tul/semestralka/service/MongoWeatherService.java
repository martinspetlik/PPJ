package tul.semestralka.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import tul.semestralka.data.Weather;


import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoWeatherService implements WeatherService{

    private final MongoOperations mongo;

    public MongoWeatherService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    @Override
    public Weather find(ObjectId objectId) {
        return mongo.findOne(Query.query(where("id").is(objectId)), Weather.class);
    }

    @Override
    public Weather add(Weather weather) {
        mongo.insert(weather);
        return weather;
    }

    public List<Weather> getAll()
    {
        return mongo.findAll(Weather.class);
    }

    @Override
    public void remove(Weather weather) {
        mongo.remove(weather);
    }

    public void removeAll() {
        mongo.dropCollection(Weather.class);
    }

    public List<Weather> findByTownId(Integer townId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("townId").is(townId));
        return mongo.find(query, Weather.class);
    }
}
