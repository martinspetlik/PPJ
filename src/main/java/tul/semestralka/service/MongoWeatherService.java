package tul.semestralka.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.aggregation.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import tul.semestralka.data.Weather;
import tul.semestralka.data.WeatherAverage;
import tul.semestralka.repositories.WeatherRepository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class MongoWeatherService{

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    public MongoTemplate mongoTemplate;

    @Value("${weather.expiration}")
    private int expiration;

    @Autowired
    public MongoWeatherService(){}

    public Weather find(ObjectId objectId) {
        return weatherRepository.findById(objectId).orElse(null);
    }


    public void add(Weather weather) {
        weather.setInsertTime(new Date());
        weatherRepository.insert(weather);
    }

    public List<Weather> getAll()
    {
        return weatherRepository.findAll();
    }


    public void remove(Weather weather) {
        weatherRepository.delete(weather);
    }

    public void removeAll() {
        weatherRepository.deleteAll();
    }

    public List<Weather> findByTownId(Integer townId) {
        Sort sort = new Sort(Sort.Direction.ASC, "time");
        return weatherRepository.findByTownId(townId, sort);
    }

    public void update(Weather weather){weatherRepository.save(weather);
    }

    public boolean exists(Weather weather){
        if (weather == null) return false;
        if (weather.getId() != null) {
            return weatherRepository.existsById(weather.getId());
        } else {
            return weatherRepository.existsByTimeAndTownId(weather.getTime(), weather.getTownId());
        }
    }

    public boolean existsByTownId(Integer townId) {
        return weatherRepository.existsByTownId(townId);
    }

    public Weather getActual(Integer townId) {
        return weatherRepository.findFirstByTownIdOrderByTimeDesc(townId);
    }

    public WeatherAverage getAverage(Integer townId, String period) {

        ZonedDateTime fromTime = ZonedDateTime.now();

        if (period.equals("day") ) {
            fromTime = ZonedDateTime.now().minusDays(1);
        } else if (period.equals("week")){
            fromTime = ZonedDateTime.now().minusWeeks(1);
        } else if (period.equals("fortnight")) {
            fromTime = ZonedDateTime.now().minusWeeks(2);
        }

        GroupOperation group = Aggregation.group("townId")
                                .avg("humidity").as("humidityAvg")
                                .avg("pressure").as("pressureAvg")
                                .avg("temp").as("tempAvg")
                                .avg("windDegree").as("windDegreeAvg")
                                .avg("windSpeed").as("windSpeedAvg");

        MatchOperation match = Aggregation.match(new Criteria("time").gt(fromTime).and("townId").is(townId));
        ProjectionOperation projection = Aggregation.project("tempAvg", "humidityAvg", "pressureAvg", "windDegreeAvg", "windSpeedAvg");
        Aggregation aggregation = Aggregation.newAggregation(match, group, projection);

        //Convert the aggregation result into a List
        AggregationResults<WeatherAverage> groupResults
                = mongoTemplate.aggregate(aggregation, "weather", WeatherAverage.class);

        return groupResults.getUniqueMappedResult();

    }
}
