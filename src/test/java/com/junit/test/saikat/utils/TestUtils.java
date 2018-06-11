package com.junit.test.saikat.utils;

import com.test.saikat.weather.TempParam;
import com.test.saikat.weather.WeatherParam;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by 476315 on 10/06/2018.
 */
public class TestUtils {
    public static WeatherParam buildWeatherParam() {
        WeatherParam weatherParam = new WeatherParam();
        weatherParam.setCityName("Helsinki");

        TempParam tempParam = new TempParam();
        tempParam.setTemp(32.0);
        tempParam.setMaxTemp(40.5);
        tempParam.setTimestamp(new Date().getTime());

        weatherParam.setTempParams(Arrays.asList(tempParam));

        return weatherParam;
    }
}
