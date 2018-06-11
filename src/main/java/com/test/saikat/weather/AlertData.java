package com.test.saikat.weather;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * The Class to hold alert informations
 * <p>
 * Created by Saikat Das.
 */
public class AlertData {

    private double maximumTemperature;
    private String alertDateTime;
    private String alertTextForUI;

    public double getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(double maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public String getAlertDateTime() {
        return alertDateTime;
    }

    public void setAlertDateTime(Instant alertDateTime) {
        this.alertDateTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneOffset.UTC)
                .format(alertDateTime);
    }

    public String getAlertTextForUI() {
        return alertTextForUI;
    }

    public void setAlertTextForUI(String alertTextForUI) {
        this.alertTextForUI = alertTextForUI;
    }
}
