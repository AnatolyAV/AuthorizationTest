
package ru.andreev_av.authorizationtest.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherCurrentDataModel {

    @SerializedName("main")
    @Expose
    private MainDataModel mMainDataModel;
    @SerializedName("dt")
    @Expose
    private Long mDateTime;
    @SerializedName("id")
    @Expose
    private Integer mCityId;
    @SerializedName("name")
    @Expose
    private String mCityName;

    public WeatherCurrentDataModel(MainDataModel mainDataModel, Long dateTime, Integer cityId, String cityName) {
        mMainDataModel = mainDataModel;
        mDateTime = dateTime;
        mCityId = cityId;
        mCityName = cityName;
    }

    public MainDataModel getMainDataModel() {
        return mMainDataModel;
    }

    public void setMainDataModel(MainDataModel mainDataModel) {
        mMainDataModel = mainDataModel;
    }

    public Long getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Long dateTime) {
        mDateTime = dateTime;
    }

    public Integer getCityId() {
        return mCityId;
    }

    public void setCityId(Integer cityId) {
        mCityId = cityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

}
