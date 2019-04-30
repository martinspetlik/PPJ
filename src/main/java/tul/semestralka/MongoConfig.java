package tul.semestralka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import tul.semestralka.api.service.DownloadWeatherService;
import tul.semestralka.converter.ZonedDateTimeReadConverter;
import tul.semestralka.converter.ZonedDateTimeWriteConverter;
import tul.semestralka.data.Weather;
import tul.semestralka.service.MongoWeatherService;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class MongoConfig {

    private List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

    @Value("${weather.expiration}")
    private int expiration;

    @Autowired
    private MongoWeatherService weatherService;

    @Bean
    public MongoCustomConversions customConversions() {
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }

    @Bean
    @ConditionalOnProperty(value = "readOnlyMode", matchIfMissing = true, havingValue = "false")
    public DownloadWeatherService scheduledJob() {
        return new DownloadWeatherService();
    }


    @Bean
    @ConditionalOnProperty(value = "tests", matchIfMissing = true, havingValue = "false")
    public void afterPropertiesSet() {
        try {
            weatherService.mongoTemplate.indexOps(Weather.class).ensureIndex(new Index().on("insertTime", Sort.Direction.ASC).expire(expiration).named("TTL_INDEX"));
        } catch (UncategorizedMongoDbException e) {
            weatherService.mongoTemplate.indexOps(Weather.class).dropIndex("TTL_INDEX");
            weatherService.mongoTemplate.indexOps(Weather.class).ensureIndex(new Index().on("insertTime", Sort.Direction.ASC).expire(expiration).named("TTL_INDEX"));
        }
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}