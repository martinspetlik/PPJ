package tul.semestralka.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import tul.semestralka.data.Weather;

import java.util.List;
import java.util.stream.Stream;

public interface WeatherRepository extends MongoRepository<Weather, ObjectId> {

    List<Weather> findByTime(Long time, Pageable pageable);

    //Stream<Weather> streamAll();

}