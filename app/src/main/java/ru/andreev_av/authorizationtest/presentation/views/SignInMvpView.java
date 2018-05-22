package ru.andreev_av.authorizationtest.presentation.views;

import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;

public interface SignInMvpView extends MvpView {

    void showEmptyEmailError();

    void showInvalidEmailError();

    void showEmptyPasswordError();

    void showInvalidPasswordError();

    void showHelpPasswordInput();

    void resetErrors();

    void showNotConnection();

    void hideKeyboard();

    void showProgress();

    void hideProgress();

    void showWeather(WeatherCurrent weatherCurrent);

    void showWeatherError();
}
