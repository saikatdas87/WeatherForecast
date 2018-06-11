package com.test.saikat.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.Instant;
import java.util.Map;

/**
 * The POJO class to hold temperature related details from API JSON response
 * <p>
 * Created by Saikat Das.
 */
public class TempParam {

    private double maxTemp;
    private double minTemp;
    private Instant timestamp;
    private double temp;


    @JsonSetter("dt")
    public void setTimestamp(long unixTime) {
        this.timestamp = Instant.ofEpochMilli(unixTime * 1000);
    }

    @JsonProperty("main")
    public void setMain(Map<String, Object> main) {
        setMaxTemp(Double.parseDouble(main.get("temp_max").toString()));
        setMinTemp(Double.parseDouble(main.get("temp_min").toString()));
        setTemp(Double.parseDouble(main.get("temp").toString()));
    }


    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
