package tul.semestralka.converter;


import org.springframework.core.convert.converter.Converter;

import java.time.ZonedDateTime;
import java.util.Date;

public class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, Long> {
//    @Override
//    public Date convert(ZonedDateTime zonedDateTime) {
//        return Date.from(zonedDateTime.toInstant());
//    }

    @Override
    public Long convert(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant().toEpochMilli();
    }
}
