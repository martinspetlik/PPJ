package tul.semestralka.api.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {

    @JsonProperty("speed")
    private float speed;

    @JsonProperty("deg")
    private int degree;

    public Wind() {
    }

    float getSpeed() {
        return speed;
    }

    float getDegree() {
        return degree;
    }

    @Override
    public String toString() {
        return "[speed=" + speed + "," + "degree=" + degree + "]";

    }
}

