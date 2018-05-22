package ru.andreev_av.authorizationtest.injection.component;

import dagger.Component;
import ru.andreev_av.authorizationtest.injection.ConfigPersistent;
import ru.andreev_av.authorizationtest.injection.module.ActivityModule;

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

}