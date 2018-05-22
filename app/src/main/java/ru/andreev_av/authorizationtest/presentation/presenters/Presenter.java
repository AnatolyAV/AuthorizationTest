package ru.andreev_av.authorizationtest.presentation.presenters;

import ru.andreev_av.authorizationtest.presentation.views.MvpView;

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
