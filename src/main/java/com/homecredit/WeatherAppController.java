package com.homecredit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/getweather")
public class WeatherAppController {
    private WeatherAppService weatherAppService;

    @Autowired
    public WeatherAppController(WeatherAppService weatherAppService){
        super();
        this.weatherAppService = weatherAppService;
    }

    @GetMapping
    public String getWeather(Model model)
    {
        model.addAttribute("getweather", this.weatherAppService.getWeather());
        return "getweather";
    }
}
