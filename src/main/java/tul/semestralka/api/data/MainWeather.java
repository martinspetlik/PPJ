package tul.semestralka.api.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeather {

    @JsonProperty("temp")
    private float temp;

    @JsonProperty("pressure")
    private float pressure;

    @JsonProperty("humidity")
    private float humidity;

    public MainWeather() {
    }

    public float getTemp() {
        return temp;
    }

    float getPressure() {
        return pressure;
    }

    float getHumidity() {
        return humidity;
    }

    @Override
    public String toString() {
        return "[temp=" + temp + "," +
                "pressure=" + pressure + "," +
                "humidity=" + humidity + "]";
    }
}

