package tul.semestralka.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import tul.semestralka.data.Weather;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface WeatherRepository extends MongoRepository<Weather, ObjectId> {

    List<Weather> findByTime(Long time, Pageable pageable);
    List<Weather> findByTownId(Integer townId, Sort sort);

    @Override
    boolean existsById(ObjectId objectId);

    boolean existsByTownId(Integer townId);

    boolean existsByTimeAndTownId(ZonedDateTime time, Integer townId);

    Weather findFirstByTownIdOrderByTimeAsc(Integer townId);

}