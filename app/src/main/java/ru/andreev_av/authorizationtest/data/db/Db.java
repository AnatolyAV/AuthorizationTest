package ru.andreev_av.authorizationtest.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import ru.andreev_av.authorizationtest.data.model.MainDataModel;
import ru.andreev_av.authorizationtest.data.model.WeatherCurrentDataModel;
import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;

import static ru.andreev_av.authorizationtest.data.db.Db.WeatherCurrentTable.WeatherCurrentEntry.COLUMN_CITY_ID;
import static ru.andreev_av.authorizationtest.data.db.Db.WeatherCurrentTable.WeatherCurrentEntry.COLUMN_CITY_NAME;
import static ru.andreev_av.authorizationtest.data.db.Db.WeatherCurrentTable.WeatherCurrentEntry.COLUMN_HUMIDITY;
import static ru.andreev_av.authorizationtest.data.db.Db.WeatherCurrentTable.WeatherCurrentEntry.COLUMN_PRESSURE;
import static ru.andreev_av.authorizationtest.data.db.Db.WeatherCurrentTable.WeatherCurrentEntry.COLUMN_TEMPERATURE_CURRENT;
import static ru.andreev_av.authorizationtest.data.db.Db.WeatherCurrentTable.WeatherCurrentEntry.COLUMN_TEMPERATURE_MAX;
import static ru.andreev_av.authorizationtest.data.db.Db.WeatherCurrentTable.WeatherCurrentEntry.COLUMN_TEMPERATURE_MIN;
import static ru.andreev_av.authorizationtest.data.db.Db.WeatherCurrentTable.WeatherCurrentEntry.COLUMN_TIME_MEASUREMENT;

public class Db {

    public Db() { }

    public abstract static class WeatherCurrentTable {
        public static final String SQL_CREATE_WEATHER_CURRENT_TABLE = "CREATE TABLE " + WeatherCurrentEntry.TABLE_NAME + "(" +
                WeatherCurrentEntry._ID + " INTEGER primary key autoincrement, " +
                COLUMN_CITY_ID + " INTEGER NOT NULL, " +
                COLUMN_CITY_NAME + " VARCHAR(100) NOT NULL, " +
                COLUMN_TEMPERATURE_CURRENT + " FLOAT NOT NULL, " +
                COLUMN_PRESSURE + " FLOAT NOT NULL, " +
                COLUMN_HUMIDITY + " FLOAT NOT NULL, " +
                COLUMN_TEMPERATURE_MIN + " FLOAT NOT NULL, " +
                COLUMN_TEMPERATURE_MAX + " FLOAT NOT NULL, " +
                COLUMN_TIME_MEASUREMENT + " BIGINT NOT NULL " +
                ")";

        public static abstract class WeatherCurrentEntry implements BaseColumns {
            public static final String TABLE_NAME = "weather_current";

            public static final String COLUMN_CITY_ID = "city_id";
            public static final String COLUMN_CITY_NAME = "city_name";

            public static final String COLUMN_TEMPERATURE_CURRENT = "temperature_current";
            public static final String COLUMN_PRESSURE = "pressure";
            public static final String COLUMN_HUMIDITY = "humidity";
            public static final String COLUMN_TEMPERATURE_MIN = "temperature_min";
            public static final String COLUMN_TEMPERATURE_MAX = "temperature_max";

            public static final String COLUMN_TIME_MEASUREMENT = "time_of_measurement";
        }

        public static ContentValues toContentValues(WeatherCurrentDataModel weatherCurrentDataModel) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_CITY_ID, weatherCurrentDataModel.getCityId());
            contentValues.put(COLUMN_CITY_NAME, weatherCurrentDataModel.getCityName());

            MainDataModel main = weatherCurrentDataModel.getMainDataModel();
            if (main != null) {
                contentValues.put(COLUMN_TEMPERATURE_CURRENT, main.getTemperature());
                contentValues.put(COLUMN_PRESSURE, main.getPressure());
                contentValues.put(COLUMN_HUMIDITY, main.getHumidity());
                contentValues.put(COLUMN_TEMPERATURE_MIN, main.getTemperatureMin());
                contentValues.put(COLUMN_TEMPERATURE_MAX, main.getTemperatureMax());
            }

            contentValues.put(COLUMN_TIME_MEASUREMENT, weatherCurrentDataModel.getDateTime());

            return contentValues;
        }

        public static WeatherCurrent parseCursor(Cursor cursor) {
            int cityId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CITY_ID));
            String cityName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY_NAME));
            float temperature = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_TEMPERATURE_CURRENT));
            long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TIME_MEASUREMENT));

            return WeatherCurrent.builder()
                    .cityId(cityId)
                    .cityName(cityName)
                    .temperature(temperature)
                    .dateTime(dateTime)
                    .build();
        }
    }
}
