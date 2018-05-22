
package ru.andreev_av.authorizationtest.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainDataModel {

    @SerializedName("temp")
    @Expose
    private Float mTemperature;
    @SerializedName("pressure")
    @Expose
    private Float mPressure;
    @SerializedName("humidity")
    @Expose
    private Float mHumidity;
    @SerializedName("temp_min")
    @Expose
    private Float mTemperatureMin;
    @SerializedName("temp_max")
    @Expose
    private Float mTemperatureMax;

    public MainDataModel(Float temperature, Float pressure, Float humidity, Float temperatureMin, Float temperatureMax) {
        mTemperature = temperature;
        mPressure = pressure;
        mHumidity = humidity;
        mTemperatureMin = temperatureMin;
        mTemperatureMax = temperatureMax;
    }

    public Float getTemperature() {
        return mTemperature;
    }

    public void setTemperature(Float temperature) {
        mTemperature = temperature;
    }

    public Float getPressure() {
        return mPressure;
    }

    public void setPressure(Float pressure) {
        mPressure = pressure;
    }

    public Float getHumidity() {
        return mHumidity;
    }

    public void setHumidity(Float humidity) {
        mHumidity = humidity;
    }

    public Float getTemperatureMin() {
        return mTemperatureMin;
    }

    public void setTemperatureMin(Float tempMin) {
        mTemperatureMin = tempMin;
    }

    public Float getTemperatureMax() {
        return mTemperatureMax;
    }

    public void setTemperatureMax(Float tempMax) {
        mTemperatureMax = tempMax;
    }

}
