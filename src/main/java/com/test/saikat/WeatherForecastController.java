package com.test.saikat;

import com.test.saikat.service.WeatherMonitorService;
import com.test.saikat.weather.WeatherSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.test.saikat.properties.ApplicationConstants.SUMMARY;
import static com.test.saikat.properties.ApplicationConstants.VIEW_WEATHER;

/**
 * The Controller class to monitor weather conditions
 * <p>
 * Created by Saikat Das .
 */
@Controller
public class WeatherForecastController {

    @Autowired
    private WeatherMonitorService weatherMonitorService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getWeather() {
        List<WeatherSummary> weatherSummaries = weatherMonitorService.monitorWeatherForecast();
        Map<String, Object> model = new HashMap<>();
        model.put(SUMMARY, weatherSummaries);
        return new ModelAndView(VIEW_WEATHER, model);
    }
}
