package ru.andreev_av.authorizationtest.data.remote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.andreev_av.authorizationtest.data.model.WeatherCurrentDataModel;

public interface OwmService {

    String API_KEY = "9162388bd4e78a8f4b5748cc11597c24";
    String ENDPOINT = "http://api.openweathermap.org/";

    @GET("/data/2.5/weather")
    Observable<WeatherCurrentDataModel> getWeatherCurrent(@Query("id") int cityId, @Query("units") String units, @Query("lang") String lang, @Query("appid") String apiKey);

    class Creator {

        public static OwmService newOwmService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(OwmService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(OwmService.class);
        }
    }
}
