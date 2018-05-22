package ru.andreev_av.authorizationtest.utils;

import android.support.annotation.NonNull;

import ru.andreev_av.authorizationtest.data.model.MainDataModel;
import ru.andreev_av.authorizationtest.data.model.WeatherCurrentDataModel;
import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;

public class WeatherCurrentConverter {

    public static WeatherCurrent convert(@NonNull WeatherCurrentDataModel weatherCurrentDataModel){

        int cityId = weatherCurrentDataModel.getCityId();
        String cityName = weatherCurrentDataModel.getCityName();

        MainDataModel mainDataModel = weatherCurrentDataModel.getMainDataModel();
        Float temperature = 0f;
        if (mainDataModel != null) {
            temperature = mainDataModel.getTemperature();
        }

        long dateTime = weatherCurrentDataModel.getDateTime();

        return WeatherCurrent.builder()
                .cityId(cityId)
                .cityName(cityName)
                .temperature(temperature)
                .dateTime(dateTime)
                .build();
    }
}
