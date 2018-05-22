package ru.andreev_av.authorizationtest.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.andreev_av.authorizationtest.data.remote.OwmService;
import ru.andreev_av.authorizationtest.injection.ApplicationContext;

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    OwmService provideOwmService() {
        return OwmService.Creator.newOwmService();
    }

}
