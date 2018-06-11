package com.test.saikat.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * The POJO class to hold all configured property values
 * <p>
 * Created by Saikat Das
 */

@Component
@PropertySource("classpath:application.properties")
public class ApplicationWeatherProperties {

    @Value("${weather.monitor.app.api.key}")
    private String apiKey;

    @Value("${weather.monitor.app.tempmax.limit}")
    private String tempLimit;

    @Value("${weather.monitor.app.locations}")
    private String locations;

    @Value("${weather.monitor.app.current.api}")
    private String currentWeatherApiURL;

    @Value("${weather.monitor.app.forecast.api}")
    private String weatherForecastApiURL;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTempLimit() {
        return tempLimit;
    }

    public void setTempLimit(String tempLimit) {
        this.tempLimit = tempLimit;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getCurrentWeatherApiURL() {
        return currentWeatherApiURL;
    }

    public void setCurrentWeatherApiURL(String currentWeatherApiURL) {
        this.currentWeatherApiURL = currentWeatherApiURL;
    }

    public String getWeatherForecastApiURL() {
        return weatherForecastApiURL;
    }

    public void setWeatherForecastApiURL(String weatherForecastApiURL) {
        this.weatherForecastApiURL = weatherForecastApiURL;
    }
}
