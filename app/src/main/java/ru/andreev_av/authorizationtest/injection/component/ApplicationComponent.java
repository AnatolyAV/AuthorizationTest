package ru.andreev_av.authorizationtest.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ru.andreev_av.authorizationtest.data.DataManager;
import ru.andreev_av.authorizationtest.injection.ApplicationContext;
import ru.andreev_av.authorizationtest.injection.module.ApplicationModule;
import ru.andreev_av.authorizationtest.utils.ConnectionDetector;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    ConnectionDetector connectionDetector();

    DataManager dataManager();
}
