package com.homecredit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WeatherAppService {
    private EntityManager entityManager;

    private static List<Weather> weathers = new ArrayList<>();
    private WeatherAppRepository weatherAppRepository;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date date = new Date();

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


                weathers.add(new Weather(x, cityName, cityWeather, cityTemp, date));
                //insertWeatherData(x, cityName, cityWeather, cityTemp);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.weatherAppRepository.findAll().forEach(weathers::add);
        return weathers;
    }


    public void insertWeatherData(int responseId, String cityName, String cityWeather, String cityTemp){
        System.out.println("Response ID: " + responseId + ", City: " + cityName + ", Temp: " + cityTemp + "F, Weather Forecast: " + cityWeather + ", Date/Time Inserted: " + dateFormat.format(date));
        Query query = entityManager.createNativeQuery("INSERT INTO WEATHERLOG (RESPONSEID, LOCATION, ACTUALWEATHER, TEMPERATURE, DTIMEINSERTED) VALUES(?,?,?,?,?)");
        query.setParameter(1, responseId);
        query.setParameter(2, cityName);
        query.setParameter(3, cityWeather);
        query.setParameter(4, cityTemp);
        query.setParameter(5, date);
        query.executeUpdate();
    }
}
