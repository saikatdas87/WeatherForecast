package com.test.saikat.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The POJO to hold weather details also mapped from JSON response
 * <p>
 * Created by Saikat Das.
 */

public class WeatherParam {

    private String cityName;
    private List<TempParam> tempParams = new ArrayList<>();

    @JsonSetter("list")
    public void setTempParams(List<TempParam> entries) {
        this.tempParams = entries;
    }

    @JsonProperty("city")
    public void setCity(Map<String, Object> city) {
        setCityName(city.get("name").toString());
    }

    public String getCityName() {
        return cityName;
    }


    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<TempParam> getTempParams() {
        return tempParams;
    }
}
