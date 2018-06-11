package com.test.saikat.weather;

import java.util.List;

/**
 * The POJO to hold details of summary displayed in UI
 *
 * Created by Saikat Das .
 */
public class WeatherSummary {

    private String city;
    private double temp;
    private List<String> alerts;
    private String errorMessageUI;

    public WeatherSummary(String error){
        this.errorMessageUI = error;
    }

    public WeatherSummary(String city, double temp){
        this.city = city;
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public List<String> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<String> alerts) {
        if(0 < alerts.size())
            this.alerts = alerts;
    }

    public String getErrorMessageUI() {
        return errorMessageUI;
    }

    public void setErrorMessageUI(String errorMessageUI) {
        this.errorMessageUI = errorMessageUI;
    }
}
