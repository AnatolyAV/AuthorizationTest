package ru.andreev_av.authorizationtest.domain.model;

import java.io.Serializable;

public class WeatherCurrent implements Serializable {

    public final static int WEATHER_EMPTY_CITY_ID = -1;

    private Integer mCityId;
    private String mCityName;
    private Float mTemperature;
    private Long mDateTime;

    private WeatherCurrent(Integer cityId) {
        mCityId = cityId;
    }

    private WeatherCurrent(Integer cityId,
                           String cityName, Float temperature,Long dateTime) {
        mCityId = cityId;
        mCityName = cityName;
        mTemperature = temperature;
        mDateTime = dateTime;
    }

    public Integer getCityId() {
        return mCityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public Float getTemperature() {
        return mTemperature;
    }

    public Long getDateTime() {
        return mDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherCurrent that = (WeatherCurrent) o;

        if (mCityId != null ? !mCityId.equals(that.mCityId) : that.mCityId != null) return false;
        if (mCityName != null ? !mCityName.equals(that.mCityName) : that.mCityName != null)
            return false;
        if (mTemperature != null ? !mTemperature.equals(that.mTemperature) : that.mTemperature != null)
            return false;
        return mDateTime != null ? mDateTime.equals(that.mDateTime) : that.mDateTime == null;
    }

    @Override
    public int hashCode() {
        int result = mCityId != null ? mCityId.hashCode() : 0;
        result = 31 * result + (mCityName != null ? mCityName.hashCode() : 0);
        result = 31 * result + (mTemperature != null ? mTemperature.hashCode() : 0);
        result = 31 * result + (mDateTime != null ? mDateTime.hashCode() : 0);
        return result;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer mCityId;
        private String mCityName;
        private Float mTemperature;
        private Long mDateTime;

        private Builder() {
        }

        public Builder cityId(Integer cityId) {
            mCityId = cityId;
            return this;
        }

        public Builder cityName(String cityName) {
            mCityName = cityName;
            return this;
        }

        public Builder temperature(Float temperature) {
            mTemperature = temperature;
            return this;
        }

        public Builder dateTime(Long dateTime) {
            mDateTime = dateTime;
            return this;
        }

        public WeatherCurrent build() {
            return new WeatherCurrent(mCityId, mCityName,mTemperature, mDateTime);
        }
        public WeatherCurrent buildEmpty() {
            return new WeatherCurrent(WEATHER_EMPTY_CITY_ID);
        }
    }
}
