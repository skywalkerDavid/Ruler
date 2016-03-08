package com.skywalker.ruler;

import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AndroidModule {

    @Provides
    @Singleton Bus provideMessageBus() {
        return new Bus();
    }

}
