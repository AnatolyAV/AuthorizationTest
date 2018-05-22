package ru.andreev_av.authorizationtest;

import android.app.Application;
import android.content.Context;

import ru.andreev_av.authorizationtest.injection.component.ApplicationComponent;
import ru.andreev_av.authorizationtest.injection.component.DaggerApplicationComponent;
import ru.andreev_av.authorizationtest.injection.module.ApplicationModule;


public class SignInApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static SignInApplication get(Context context) {
        return (SignInApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
