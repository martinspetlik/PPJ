package tul.semestralka;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import tul.semestralka.converter.ZonedDateTimeReadConverter;
import tul.semestralka.converter.ZonedDateTimeWriteConverter;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    private List<Converter<?,?>> converters = new ArrayList<Converter<?,?>>();

    @Bean
    public MongoCustomConversions customConversions() {
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());

        return new MongoCustomConversions(converters);
    }

}