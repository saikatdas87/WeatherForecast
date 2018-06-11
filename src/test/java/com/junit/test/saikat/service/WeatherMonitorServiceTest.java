package com.junit.test.saikat.service;

import com.junit.test.saikat.utils.TestUtils;
import com.test.saikat.properties.ApplicationWeatherProperties;
import com.test.saikat.service.WeatherAlertService;
import com.test.saikat.service.WeatherMonitorService;
import com.test.saikat.weather.WeatherParam;
import com.test.saikat.weather.WeatherSummary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static com.test.saikat.properties.ApplicationConstants.WEATHER_API_READ_ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Test class to test WeatherMonitorService class methods
 *
 * Created by Saikat Das on 09/06/2018.
 */
@RunWith(SpringRunner.class)
@RestClientTest(WeatherMonitorService.class)
@SpringBootTest(classes = WeatherMonitorService.class)
public class WeatherMonitorServiceTest {

    RestTemplate restTemplate;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public WeatherMonitorService service() {
            return new WeatherMonitorService();
        }
    }

    @Before
    public void setUp() {
        restTemplate = mock(RestTemplate.class);
        server = MockRestServiceServer.createServer(restTemplate);
        given(properties.getLocations()).willReturn("Helsinki");
        given(properties.getApiKey()).willReturn("xyz");
        given(properties.getCurrentWeatherApiURL()).willReturn("currentHelsinki");
        given(properties.getWeatherForecastApiURL()).willReturn("forecastHelsinki");
        given(properties.getTempLimit()).willReturn("45.0");
        given(restTemplateBuilder.build()).willReturn(restTemplate);
    }

    @MockBean
    private ApplicationWeatherProperties properties;

    @MockBean
    private WeatherAlertService weatherAlertService;

    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private WeatherMonitorService service;

    @Autowired
    private MockRestServiceServer server;


    @Test
    public void testMonitorWeatherForecastException() {
        given(restTemplate.exchange(Mockito.<URI>any(), Mockito.<HttpMethod>eq(HttpMethod.GET),
                Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any())).willThrow(RestClientException.class);
        List<WeatherSummary> summaries = service.monitorWeatherForecast();
        assertEquals(summaries.get(0).getErrorMessageUI(), WEATHER_API_READ_ERROR);
    }

    @Test
    public void testMonitorWeatherForecastSuccess() {
        WeatherParam weatherParam = TestUtils.buildWeatherParam();
        ResponseEntity responseEntityWeather = mock(ResponseEntity.class);
        ResponseEntity responseEntityForecast = mock(ResponseEntity.class);
        given(restTemplate.exchange(Mockito.<URI>any(), Mockito.<HttpMethod>eq(HttpMethod.GET),
                Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any())).willReturn(responseEntityForecast, responseEntityWeather);
        given(responseEntityForecast.getBody()).willReturn(weatherParam);
        given(responseEntityWeather.getBody()).willReturn(weatherParam.getTempParams().get(0));
        List<WeatherSummary> summaries = service.monitorWeatherForecast();
        assertNotNull(summaries);
        assertEquals(summaries.get(0).getCity(), "Helsinki");
        assertEquals(String.valueOf(summaries.get(0).getTemp()), String.valueOf(32.0));
    }

}
