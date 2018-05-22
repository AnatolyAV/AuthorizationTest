package ru.andreev_av.authorizationtest.presentation.presenters;

import android.text.TextUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.andreev_av.authorizationtest.data.DataManager;
import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;
import ru.andreev_av.authorizationtest.injection.ConfigPersistent;
import ru.andreev_av.authorizationtest.presentation.views.SignInMvpView;
import ru.andreev_av.authorizationtest.utils.ConnectionDetector;
import ru.andreev_av.authorizationtest.utils.RxUtils;
import ru.andreev_av.authorizationtest.utils.ValidatorUtils;
import timber.log.Timber;

@ConfigPersistent
public class SignInPresenter extends BasePresenter<SignInMvpView> {

    private final static int CITY_ID = 536203; // Sankt-Peterburg

    private final DataManager mDataManager;
    private final ConnectionDetector mConnectionDetector;
    private Disposable mDisposable;

    @Inject
    public SignInPresenter(DataManager dataManager, ConnectionDetector connectionDetector) {
        mDataManager = dataManager;
        mConnectionDetector = connectionDetector;
    }

    @Override
    public void attachView(SignInMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void onTextChangedEmail() {
        getMvpView().resetErrors();
    }

    public void onTextChangedPassword() {
        getMvpView().resetErrors();
    }

    public void onTouchPasswordIconHelp() {
        getMvpView().showHelpPasswordInput();
    }

    public void signIn(String email, String password) {
        if (checkValidEmailAndPassword(email, password)) {
            loadWeather(CITY_ID);
        }
    }

    private boolean checkValidEmailAndPassword(String email, String password) {
        if (checkValidEmail(email) && checkValidPassword(password)) {
            return true;
        }
        return false;
    }

    private boolean checkValidEmail(String email) {
        boolean valid;

        if (TextUtils.isEmpty(email)) {
            getMvpView().showEmptyEmailError();
            valid = false;
        } else if (!ValidatorUtils.checkValidEmail(email)) {
            getMvpView().showInvalidEmailError();
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }

    private boolean checkValidPassword(String password) {
        boolean valid;

        if (TextUtils.isEmpty(password)) {
            valid = false;
            getMvpView().showEmptyPasswordError();
        } else if (!ValidatorUtils.checkValidPassword(password)) {
            valid = false;
            getMvpView().showInvalidPasswordError();
        } else {
            valid = true;
        }

        return valid;
    }

    public void loadWeather(int cityId) {
        checkViewAttached();
        RxUtils.dispose(mDisposable);
        if (mConnectionDetector.isNetworkAvailableAndConnected()) {
            mDataManager.syncWeather(cityId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            getMvpView().showProgress();
                        }
                    })
                    .doAfterTerminate(new Action() {
                        @Override
                        public void run() throws Exception {
                            getMvpView().hideProgress();
                        }
                    })
                    .subscribe(new Observer<WeatherCurrent>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(@NonNull WeatherCurrent weatherCurrent) {
                            getMvpView().hideKeyboard();
                            if (weatherCurrent.getCityId()!= WeatherCurrent.WEATHER_EMPTY_CITY_ID) {
                                getMvpView().showWeather(weatherCurrent);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Timber.w(e, "Error syncing.");
                            getMvpView().showWeatherError();
                        }

                        @Override
                        public void onComplete() {
                            Timber.i("Synced successfully!");
                        }
                    });
        } else {
            getMvpView().showNotConnection();

            mDataManager.getWeather()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            getMvpView().showProgress();
                        }
                    })
                    .doAfterTerminate(new Action() {
                        @Override
                        public void run() throws Exception {
                            getMvpView().hideProgress();
                        }
                    })
                    .subscribe(new Observer<WeatherCurrent>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(@NonNull WeatherCurrent weatherCurrent) {
                            getMvpView().hideKeyboard();
                            if (weatherCurrent.getCityId()!= WeatherCurrent.WEATHER_EMPTY_CITY_ID) {
                                getMvpView().showWeather(weatherCurrent);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Timber.e(e, "There was an error loading the weather.");
                            getMvpView().showWeatherError();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
