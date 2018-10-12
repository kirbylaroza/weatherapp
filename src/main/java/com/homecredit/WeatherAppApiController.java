package com.homecredit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WeatherAppApiController {

    private WeatherAppService weatherAppService;

    @Autowired
    public WeatherAppApiController(WeatherAppService weatherAppService){
        super();
        this.weatherAppService = weatherAppService;
    }

    @GetMapping("getweather")
    public List<Weather> getWeather(){
        return this.weatherAppService.getWeather();
    }

    @GetMapping("getweathertable")
    public List<Weather> getWeatherdb(){
        return this.weatherAppService.getWeather();
    }

}
