package com.skywalker.ruler;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(
    modules = {
        AndroidModule.class
    }
)
public interface AppComponent {
    void inject(RulerActivityFragment fragment);
}
