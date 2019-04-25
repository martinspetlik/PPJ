package tul.semestralka.converter;


import org.springframework.core.convert.converter.Converter;
import java.time.ZonedDateTime;

public class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, Long> {

    @Override
    public Long convert(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant().toEpochMilli();
    }
}
