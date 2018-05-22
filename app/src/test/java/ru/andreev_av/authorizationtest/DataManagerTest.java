package ru.andreev_av.authorizationtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import ru.andreev_av.authorizationtest.data.DataManager;
import ru.andreev_av.authorizationtest.data.db.DatabaseHelper;
import ru.andreev_av.authorizationtest.data.model.WeatherCurrentDataModel;
import ru.andreev_av.authorizationtest.data.remote.OwmService;
import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;
import ru.andreev_av.authorizationtest.utils.WeatherCurrentConverter;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    DatabaseHelper mMockDatabaseHelper;
    @Mock
    OwmService mMockOwnService;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockOwnService,
                mMockDatabaseHelper);
    }

    @Test
    public void syncWeatherCurrentEmitsValues() {
        WeatherCurrentDataModel weatherCurrentDataModel = TestDataFactory.createWeatherCurrentDataModel();
        stubSyncWeatherCurrentHelperCalls(weatherCurrentDataModel);

        TestObserver<WeatherCurrent> result = new TestObserver<>();
        mDataManager.syncWeather(TestDataFactory.CITY_ID).subscribe(result);
        result.assertNoErrors();
    }

    @Test
    public void syncWeatherCurrentCallsApiAndDatabase() {
        WeatherCurrentDataModel weatherCurrentDataModel = TestDataFactory.createWeatherCurrentDataModel();
        stubSyncWeatherCurrentHelperCalls(weatherCurrentDataModel);

        mDataManager.syncWeather(TestDataFactory.CITY_ID).subscribe();
        // Verify right calls to helper methods
        verify(mMockOwnService).getWeatherCurrent(TestDataFactory.CITY_ID, "metric", Locale.getDefault().getLanguage(), OwmService.API_KEY);
        verify(mMockDatabaseHelper).setWeatherCurrent(weatherCurrentDataModel);
    }

    @Test
    public void syncWeatherCurrentDoesNotCallDatabaseWhenApiFails() {
        when(mMockOwnService.getWeatherCurrent(TestDataFactory.CITY_ID, "metric", Locale.getDefault().getLanguage(), OwmService.API_KEY))
                .thenReturn(Observable.<WeatherCurrentDataModel>error(new RuntimeException()));

        mDataManager.syncWeather(TestDataFactory.CITY_ID).subscribe(new TestObserver<WeatherCurrent>());
        // Verify right calls to helper methods
        verify(mMockOwnService).getWeatherCurrent(TestDataFactory.CITY_ID, "metric", Locale.getDefault().getLanguage(), OwmService.API_KEY);
        verify(mMockDatabaseHelper, never()).setWeatherCurrent(ArgumentMatchers.<WeatherCurrentDataModel>any());
    }

    private void stubSyncWeatherCurrentHelperCalls(WeatherCurrentDataModel weatherCurrentDataModel) {
        when(mMockOwnService.getWeatherCurrent(TestDataFactory.CITY_ID, "metric", Locale.getDefault().getLanguage(), OwmService.API_KEY))
                .thenReturn(Observable.just(weatherCurrentDataModel));
        when(mMockDatabaseHelper.setWeatherCurrent(weatherCurrentDataModel))
                .thenReturn(Observable.just(WeatherCurrentConverter.convert(weatherCurrentDataModel)));
    }

}
