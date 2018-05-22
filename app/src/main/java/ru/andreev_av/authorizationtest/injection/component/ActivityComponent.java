package ru.andreev_av.authorizationtest.injection.component;

import dagger.Subcomponent;
import ru.andreev_av.authorizationtest.injection.PerActivity;
import ru.andreev_av.authorizationtest.injection.module.ActivityModule;
import ru.andreev_av.authorizationtest.presentation.activities.SignInActivity;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SignInActivity signInActivity);

}
