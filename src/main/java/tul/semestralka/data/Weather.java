package tul.semestralka.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@Document(collection = "weather")
public class Weather {

    @Id
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;
    private int townId;
    @Min(value=-274)
    @Max(value=200)
    private float temp;
    @Min(value=0)
    @Max(value=100000)
    private float pressure;
    @Min(value=0)
    @Max(value=100)
    private float humidity;
    @Min(value=0)
    @Max(value=100000)
    private float windSpeed;
    @Min(value=-360)
    @Max(value=360)
    private float windDegree;
    @JsonSerialize(using= ToStringSerializer.class)
    private ZonedDateTime time;

    public Weather(){

    }

    public Weather(int townId, float temp, float pressure, float humidity, float windSpeed, float windDegree) {
        this.townId = townId;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windDegree = windDegree;
        this.windSpeed = windSpeed;
        this.time = ZonedDateTime.now();
    }

    public Weather(int townId, float temp, float pressure, float humidity, float windSpeed, float windDegree, ZonedDateTime time) {
        this.townId = townId;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windDegree = windDegree;
        this.windSpeed = windSpeed;
        this.time = time;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(float windDegree) {
        this.windDegree = windDegree;
    }

    public int getTownId() {
        return townId;
    }

    public void setTownId(int townId) {
        this.townId = townId;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[id=" + id +  ", " +
                "townId=" + townId + "," +
                "temp=" + temp + "," +
                "pressure=" + pressure + "," +
                "humidity=" + humidity + "," +
                "windSpeed=" + windSpeed + "," +
                "windDegree=" + windDegree + "," +
                "time=" + time + "]";
    }


    public String formattedTime()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss z");
        return time.format(formatter);
    }
}
