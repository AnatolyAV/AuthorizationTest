package ru.andreev_av.authorizationtest.data;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import ru.andreev_av.authorizationtest.data.db.DatabaseHelper;
import ru.andreev_av.authorizationtest.data.model.WeatherCurrentDataModel;
import ru.andreev_av.authorizationtest.data.remote.OwmService;
import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;

@Singleton
public class DataManager {

    private final OwmService mOwmService;
    private final DatabaseHelper mDatabaseHelper;

    @Inject
    public DataManager(OwmService owmService, DatabaseHelper databaseHelper) {
        mOwmService = owmService;
        mDatabaseHelper = databaseHelper;
    }

    public Observable<WeatherCurrent> syncWeather(int cityId) {
        return mOwmService.getWeatherCurrent(cityId, "metric", Locale.getDefault().getLanguage(), OwmService.API_KEY).concatMap(new Function<WeatherCurrentDataModel, ObservableSource<? extends WeatherCurrent>>() {
            @Override
            public ObservableSource<? extends WeatherCurrent> apply(@NonNull WeatherCurrentDataModel weatherCurrentDataModel)
                    throws Exception {
                return mDatabaseHelper.setWeatherCurrent(weatherCurrentDataModel);
            }
        });
    }

    public Observable<WeatherCurrent> getWeather() {
        return mDatabaseHelper.getWeatherCurrent();
    }
}
