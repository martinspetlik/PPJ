package tul.semestralka.api.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApi {

    @JsonProperty("main")
    private MainWeather main;

    @JsonProperty("wind")
    private Wind wind;

    public WeatherApi() {
    }

    public MainWeather getMainWeather() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "main=" + main +
                "wind=" + wind +
                '}';
    }

    public float getTemp() {
        return main.getTemp();
    }

    public float getPressure() {
        return main.getPressure();
    }

    public float getHumidity() {
        return main.getHumidity();
    }

    public float getWindSpeed() {
        return wind.getSpeed();
    }

    public float getWindDegree() {
        return wind.getDegree();
    }


}