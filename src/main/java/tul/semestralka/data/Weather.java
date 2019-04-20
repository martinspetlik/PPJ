package tul.semestralka.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Document(collection = "weather")
public class Weather {

    @Id
    private ObjectId id;

    private int townId;

    private double temp;
    private float pressure;
    private float humidity;
    private float windSpeed;
    private float windDegree;
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

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
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
        return "[townId=" + townId + "," +
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
