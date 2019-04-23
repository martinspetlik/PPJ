package tul.semestralka;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.index.Index;
import tul.semestralka.converter.ZonedDateTimeReadConverter;
import tul.semestralka.converter.ZonedDateTimeWriteConverter;
import tul.semestralka.data.Weather;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class MongoConfig {

    private List<Converter<?,?>> converters = new ArrayList<Converter<?,?>>();

    @Value("${weather.expiration}")
    private int expiration;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public MongoCustomConversions customConversions() {
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }

    @Bean
    public void afterPropertiesSet() {
        try {
            mongoTemplate.indexOps(Weather.class).ensureIndex(new Index().on("insertTime", Sort.Direction.ASC).expire(expiration).named("TTL_INDEX"));
        } catch (UncategorizedMongoDbException e) {
            mongoTemplate.indexOps(Weather.class).dropIndex("TTL_INDEX");
            mongoTemplate.indexOps(Weather.class).ensureIndex(new Index().on("insertTime", Sort.Direction.ASC).expire(expiration).named("TTL_INDEX"));
        }
    }
}