package ru.andreev_av.authorizationtest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import ru.andreev_av.authorizationtest.data.DataManager;
import ru.andreev_av.authorizationtest.data.model.WeatherCurrentDataModel;
import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;
import ru.andreev_av.authorizationtest.presentation.presenters.SignInPresenter;
import ru.andreev_av.authorizationtest.presentation.views.SignInMvpView;
import ru.andreev_av.authorizationtest.util.RxSchedulersOverrideRule;
import ru.andreev_av.authorizationtest.utils.ConnectionDetector;
import ru.andreev_av.authorizationtest.utils.WeatherCurrentConverter;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SignInPresenterTest {

    @Mock
    SignInMvpView mMockSignInMvpView;
    @Mock
    DataManager mMockDataManager;
    @Mock
    ConnectionDetector mConnectionDetector;

    private SignInPresenter mSignInPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mSignInPresenter = new SignInPresenter(mMockDataManager,mConnectionDetector);
        mSignInPresenter.attachView(mMockSignInMvpView);
    }

    @After
    public void tearDown() {
        mSignInPresenter.detachView();
    }

    @Test
    public void loadWeatherReturnsWeather() {
        WeatherCurrentDataModel weatherCurrentDataModel = TestDataFactory.createWeatherCurrentDataModel();

        WeatherCurrent weatherCurrent = WeatherCurrentConverter.convert(weatherCurrentDataModel);

        when(mMockDataManager.getWeather())
                .thenReturn(Observable.just(weatherCurrent));

        mSignInPresenter.loadWeather(TestDataFactory.CITY_ID);
        verify(mMockSignInMvpView).showWeather(weatherCurrent);
        verify(mMockSignInMvpView, never()).showWeatherError();
    }

    @Test
    public void loadWeatherFails() {
        when(mMockDataManager.getWeather())
                .thenReturn(Observable.<WeatherCurrent>error(new RuntimeException()));

        mSignInPresenter.loadWeather(TestDataFactory.CITY_ID);
        verify(mMockSignInMvpView).showWeatherError();
        verify(mMockSignInMvpView, never()).showWeather(ArgumentMatchers.<WeatherCurrent>any());
    }

}
