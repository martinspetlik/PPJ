package tul.semestralka.data;

public class WeatherAverage {
    private float tempAvg;
    private float pressureAvg;
    private float humidityAvg;
    private float windSpeedAvg;
    private float windDegreeAvg;

    public WeatherAverage() {
    }

    public WeatherAverage(float tempAvg, float pressureAvg, float humidityAvg, float windSpeedAvg, float windDegreeAvg) {
        this.tempAvg = tempAvg;
        this.pressureAvg = pressureAvg;
        this.humidityAvg = humidityAvg;
        this.windSpeedAvg = windSpeedAvg;
        this.windDegreeAvg = windDegreeAvg;
    }

    public float getTempAvg() {
        return tempAvg;
    }

    public void setTempAvg(float tempAvg) {
        this.tempAvg = tempAvg;
    }

    public float getPressureAvg() {
        return pressureAvg;
    }

    public void setPressureAvg(float pressureAvg) {
        this.pressureAvg = pressureAvg;
    }

    public float getHumidityAvg() {
        return humidityAvg;
    }

    public void setHumidityAvg(float humidityAvg) {
        this.humidityAvg = humidityAvg;
    }

    public float getWindSpeedAvg() {
        return windSpeedAvg;
    }

    public void setWindSpeedAvg(float windSpeedAvg) {
        this.windSpeedAvg = windSpeedAvg;
    }

    public float getWindDegreeAvg() {
        return windDegreeAvg;
    }

    public void setWindDegreeAvg(float windDegreeAvg) {
        this.windDegreeAvg = windDegreeAvg;
    }
}
