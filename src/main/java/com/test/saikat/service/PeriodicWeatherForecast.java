package com.test.saikat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The Class to run periodically and monitor future weather conditions frequently
 * <p>
 * Created by Saikat Das .
 */
@Component
public class PeriodicWeatherForecast {

    @Autowired
    private WeatherMonitorService weatherMonitorService;

    @Scheduled(fixedDelayString = "${weather.monitor.app.schedule.period}000")
    public void run() {
        weatherMonitorService.monitorWeatherForecast();
    }
}
