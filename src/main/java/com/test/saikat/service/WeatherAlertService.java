package com.test.saikat.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.saikat.weather.Alert;
import com.test.saikat.weather.AlertData;
import com.test.saikat.weather.TempParam;
import com.test.saikat.weather.WeatherParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.test.saikat.properties.ApplicationConstants.*;

/**
 * The class to validate next 5 days temperature and log possible alerts
 * <p>
 * Created by Saikat Das .
 */
@Service
public class WeatherAlertService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherAlertService.class);

    /**
     * Method to monitor extreme weather conditions for each city and return possible alerts to be displayed in UI
     *
     * @param weatherParam
     * @return List<String>
     */
    public List<String> validateFutureWeather(final WeatherParam weatherParam, final double highTempLimit) {
        Alert alertToLog = new Alert();
        alertToLog.setLocation(weatherParam.getCityName());
        weatherParam.getTempParams().forEach(tempParam -> {
            AlertData alertData = returnAlertIfTemperatureLimitExceeds(tempParam, highTempLimit);
            if (null != alertData) {
                alertToLog.getAlerts().add(alertData);
            }
        });

        // Log alert data in JSON and return alert string for UI
        if (!alertToLog.getAlerts().isEmpty()) {
            logAlert(alertToLog);
            return alertToLog.getAlerts().stream().map(alertData -> alertData.getAlertTextForUI()).collect(Collectors.toList());
        }

        LOG.info(WEATHER_LOG_NORMAL, weatherParam.getCityName());
        return null;
    }

    /**
     * The method compares future weather conditions with configured limit and logs correspondingly
     *
     * @param tempParam
     * @return AlertData
     */
    private AlertData returnAlertIfTemperatureLimitExceeds(final TempParam tempParam, final double highTempLimit) {
        if (tempParam.getMaxTemp() > highTempLimit) {
            return buildAlertData(tempParam, highTempLimit);
        }
        return null;
    }

    /**
     * Method to log the alert in json format
     *
     * @param alert
     */
    private void logAlert(final Alert alert) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String alertJson = gson.toJson(alert);
        LOG.info(ALERT_TEXT, alert.getLocation(), alertJson);
    }

    /**
     * Method to build AlertData when temperature exceeds defined limit
     *
     * @param tempParam
     * @param highTempLimit
     * @return AlertData
     */
    private AlertData buildAlertData(final TempParam tempParam, final double highTempLimit) {
        AlertData alertData = new AlertData();
        String alertText = HIGH_TEMP_WEATHER_ALERT + highTempLimit + "°C on " + tempParam.getTimestamp() + " UTC";
        alertData.setAlertTextForUI(alertText);
        alertData.setMaximumTemperature(tempParam.getMaxTemp());
        alertData.setAlertDateTime(tempParam.getTimestamp());
        return alertData;
    }
}
