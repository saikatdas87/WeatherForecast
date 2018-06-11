package com.junit.test.saikat.service;

import com.junit.test.saikat.utils.TestUtils;
import com.test.saikat.service.WeatherAlertService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by Saikat Das.
 */

@RunWith(SpringRunner.class)
public class WeatherAlertServiceTest {

    @Test
    public void testValidateFutureWeatherTempExceeds() {
        WeatherAlertService service = new WeatherAlertService();
        List<String> alerts = service.validateFutureWeather(TestUtils.buildWeatherParam(), 35.0);
        assertTrue(!alerts.isEmpty());
    }

    @Test
    public void testValidateFutureWeatherPermisible() {
        WeatherAlertService service = new WeatherAlertService();
        List<String> alerts = service.validateFutureWeather(TestUtils.buildWeatherParam(), 55.0);
        assertNull(alerts);
    }

}
