package ru.andreev_av.authorizationtest;

import java.util.UUID;

import ru.andreev_av.authorizationtest.data.model.MainDataModel;
import ru.andreev_av.authorizationtest.data.model.WeatherCurrentDataModel;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    private final static float TEMPERATURE = 21f;
    private final static float PRESSURE = 1028f;
    private final static float HUMIDITY = 28f;
    private final static float TEMPERATURE_MIN = 15f;
    private final static float TEMPERATURE_MAX = 25f;

    private final static long DATE_TIME = 1526905800L;
    protected final static int CITY_ID = 536203;
    private final static String CITY_NAME = "Sankt-Peterburg";

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static WeatherCurrentDataModel createWeatherCurrentDataModel() {

        MainDataModel mainDataModel = new MainDataModel( TEMPERATURE, PRESSURE, HUMIDITY, TEMPERATURE_MIN, TEMPERATURE_MAX);
        return new WeatherCurrentDataModel(mainDataModel, DATE_TIME, CITY_ID, CITY_NAME);
    }
}