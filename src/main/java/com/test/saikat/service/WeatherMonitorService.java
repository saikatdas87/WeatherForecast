package com.test.saikat.service;


import com.test.saikat.properties.ApplicationWeatherProperties;
import com.test.saikat.weather.TempParam;
import com.test.saikat.weather.WeatherParam;
import com.test.saikat.weather.WeatherSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.test.saikat.properties.ApplicationConstants.LOG_EXCEPTION_MESSAGE;
import static com.test.saikat.properties.ApplicationConstants.MONITOR_FORECAST;
import static com.test.saikat.properties.ApplicationConstants.WEATHER_API_READ_ERROR;

/**
 * The Service class to fetch current weather and validate future weather for next 5 days of any possible
 * high temperature and log corresponding or create alerts.
 * <p>
 * Created by Saikat Das.
 */
@Service
public class WeatherMonitorService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherMonitorService.class);

    @Autowired
    private WeatherAlertService weatherAlertService;
    @Autowired
    private ApplicationWeatherProperties properties;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    /**
     * The main service method to find current weather to display in UI and fetch weather forecast. In case of any severe weather
     * conditions log proper alerts and create alert message to be displayed in UI
     * Also handles any network exceptions from Weather API
     *
     * @return List<WeatherSummary>
     */
    @Scheduled(fixedDelayString = "${weather.monitor.app.schedule.period}000")
    public List<WeatherSummary> monitorWeatherForecast() {
        List<WeatherParam> weatherForecastParamsList = new ArrayList<>();
        List<WeatherSummary> weatherSummaries = new ArrayList<>();

        // Read the configured APIKEY, Locations, URLs, tempLimit etc.
        final String apiKey = properties.getApiKey();
        final String locations = properties.getLocations();
        final String CURRENT_WEATHER_URL = properties.getCurrentWeatherApiURL();
        final String FORECAST_URL = properties.getWeatherForecastApiURL();
        final double highTempLimit = Double.valueOf(properties.getTempLimit());

        /* For all configured cities find current weather to display in UI and fetch weather forecast.
         Also validate weather forecast if on any instance maximum temperature exceeds the configured limit.
         In case weather crosses the limit log the occurrence with Alert */
        try {
            Arrays.stream(locations.split(",")).forEach(city -> {
                LOG.info(MONITOR_FORECAST, city);
                WeatherParam weatherForecastParameters = fetch(city, apiKey, WeatherParam.class, FORECAST_URL);
                weatherForecastParamsList.add(weatherForecastParameters);

                // service call to monitor future weather for any possible alerts
                List<String> futureWeatherAlerts = weatherAlertService.validateFutureWeather(weatherForecastParameters, highTempLimit);

                TempParam currentWeatherParameters = fetch(city, apiKey, TempParam.class, CURRENT_WEATHER_URL);
                WeatherSummary cityWeatherSummary = new WeatherSummary(city, currentWeatherParameters.getTemp());

                if (null != futureWeatherAlerts)
                    cityWeatherSummary.setAlerts(futureWeatherAlerts);

                weatherSummaries.add(cityWeatherSummary);
            });
        } catch (RestClientException re) {
            WeatherSummary errorWeatherSummary = new WeatherSummary(WEATHER_API_READ_ERROR);
            weatherSummaries.add(errorWeatherSummary);
            LOG.error(LOG_EXCEPTION_MESSAGE, re.getMessage());
        }
        return weatherSummaries;
    }

    /**
     * The method to call the Weather API and store the required response data in corresponding POJO
     *
     * @param city
     * @param apiKey
     * @param responseType
     * @param url
     * @param <T>
     * @return <T>
     * @throws RestClientException
     */
    private <T> T fetch(String city, String apiKey, Class<T> responseType, String url) throws RestClientException {
        URI forecastUrl = new UriTemplate(url).expand(city, apiKey);
        RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, forecastUrl);
        RestTemplate template = restTemplateBuilder.build();
        ResponseEntity<T> forecastResponse = template.exchange(forecastUrl, HttpMethod.GET, request, responseType);
        return forecastResponse.getBody();
    }
}
