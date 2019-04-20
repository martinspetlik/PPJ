package tul.semestralka.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class ZonedDateTimeReadConverter implements Converter<Long, ZonedDateTime> {
//    @Override
//    public ZonedDateTime convert(Date date) {
//        return date.toInstant().atZone(ZoneOffset.UTC);
//    }

    @Override
    public ZonedDateTime convert(Long miliseconds) {

        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(miliseconds),
                ZoneId.systemDefault());
    }


}
