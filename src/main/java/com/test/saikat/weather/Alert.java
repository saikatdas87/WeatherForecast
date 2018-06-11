package com.test.saikat.weather;

import java.util.ArrayList;
import java.util.List;

/**
 * The POJO class to be logged as Alert in JSON format
 *
 * Created by Saikat Das.
 */
public class Alert {

    private String location;
    private List<AlertData> alerts;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<AlertData> getAlerts() {
        if (null == alerts)
            alerts = new ArrayList<>();
        return alerts;
    }

}
