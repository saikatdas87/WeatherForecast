package com.test.saikat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class to start the application
 * <p>
 * Created by Saikat Das.
 */

@SpringBootApplication
@EnableScheduling
public class WeatherForecastAlerter {
    public static void main(String[] args) {
        SpringApplication.run(WeatherForecastAlerter.class, args);
    }
}
