package com.test.saikat.service;

import com.test.saikat.weather.TempParam;
import com.test.saikat.weather.WeatherParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.test.saikat.properties.ApplicationConstants.HIGH_TEMP_WEATHER_ALERT;
import static com.test.saikat.properties.ApplicationConstants.HIGH_TEMP_WEATHER_ALERT_LOG;
import static com.test.saikat.properties.ApplicationConstants.WEATHER_LOG_NORMAL;

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
        List<String> alerts = new ArrayList<>();
        weatherParam.getTempParams().forEach(tempParam -> {
            String alert = logAlertsIfTemperatureLimitExceeds(tempParam, weatherParam.getCityName(), highTempLimit);
            if (StringUtils.isNotEmpty(alert))
                alerts.add(alert);
        });
        if (alerts.isEmpty())
            LOG.info(WEATHER_LOG_NORMAL, weatherParam.getCityName());
        return alerts;
    }

    /**
     * The method compares future weather conditions with configured limit and logs correspondingly
     *
     * @param tempParam
     * @param cityName
     * @return String
     */
    private String logAlertsIfTemperatureLimitExceeds(final TempParam tempParam, final String cityName, final double highTempLimit) {
        String alertText = StringUtils.EMPTY;
        Instant forecastTime = tempParam.getTimestamp();
        if (tempParam.getMaxTemp() > highTempLimit) {
            LOG.info(HIGH_TEMP_WEATHER_ALERT_LOG, cityName, forecastTime);
            alertText = HIGH_TEMP_WEATHER_ALERT + highTempLimit + "°C on " + forecastTime.toString() + " UTC";
            // TODO email alert service
        }
        return alertText;
    }
}
