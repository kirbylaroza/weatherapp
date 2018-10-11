package com.homecredit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherAppService {

    private static List<Weather> weathers = new ArrayList<>();
    private WeatherAppRepository weatherAppRepository;

    @Autowired
    public WeatherAppService(WeatherAppRepository weatherAppRepository){
        super();
        this.weatherAppRepository = weatherAppRepository;
    }


    public List<Weather> getWeather() {
        final ArrayList weatherMapURI = new ArrayList<>();
        List<Weather> weathers = new ArrayList<>();

        int x;
        weathers.clear();

        // OpenWeatherMap API Call for 3 Cities (London, Prague and San Franciso)
        weatherMapURI.add("https://api.openweathermap.org/data/2.5/weather?q=London&appid=afa4a12f7b84c14cd1cb2ceb02d626ca");
        weatherMapURI.add("https://api.openweathermap.org/data/2.5/weather?q=Prague&appid=afa4a12f7b84c14cd1cb2ceb02d626ca");
        weatherMapURI.add("https://api.openweathermap.org/data/2.5/weather?q=San Francisco&appid=afa4a12f7b84c14cd1cb2ceb02d626ca");

        for (x = 0; x < 3; x++){
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(weatherMapURI.get(x).toString(), String.class);

            JSONArray jsonArr = null;

            try {
                String cityName = "";
                String cityWeather = "";
                String cityTemp = "";

                JSONObject jObj = new JSONObject(result);

                cityName = jObj.getString("name");
                jsonArr = jObj.getJSONArray("weather");
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    cityWeather = jsonObj.getString("description");
                }

                JSONObject jsonObjTemp = new JSONObject(jObj.getString("main"));
                cityTemp = jsonObjTemp.getString("temp");

                System.out.println("City: " + cityName + ", Temp: " + cityTemp + "F, Weather Forecast: " + cityWeather);
                weathers.add(new Weather(x, cityName, cityWeather, cityTemp));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.weatherAppRepository.findAll().forEach(weathers::add);
        return weathers;
    }
}
