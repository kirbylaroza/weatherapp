package com.homecredit;

import javax.persistence.*;

@Entity
@Table(name="WEATHERLOG")
public class Weather {

    @Id
    @Column(name="ID")
    @GeneratedValue
    private long id;

    @Column(name="LOCATION")
    private String cityName;

    @Column(name="ACTUALWEATHER")
    private String actualWeather;

    @Column(name = "TEMPERATURE")
    private String temperature;

    public Weather() {
        super();
    }

    public Weather(long id, String cityName, String actualWeather, String temperature) {
        super();
        this.id = id;
        this.cityName = cityName;
        this.actualWeather = actualWeather;
        this.temperature = temperature;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getActualWeather() {
        return actualWeather;
    }

    public void setActualWeather(String actualWeather) {
        this.actualWeather = actualWeather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
