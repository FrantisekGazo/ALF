package com.f3rog.alf.dagger;

import android.app.Application;
import android.content.Context;

import com.f3rog.alf.bus.Bus;
import com.f3rog.alf.dagger.qualifier.ForApplication;
import com.f3rog.alf.job.Jobber;
import com.f3rog.alf.pref.PrefManager;
import com.f3rog.alf.utils.Utils;
import com.path.android.jobqueue.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

/**
 * Class {@link BaseAppModule}
 *
 * @author f3rog
 */
@Module(
        complete = false,
        library = true,
        injects = {
        },
        staticInjections = {
                Utils.class,
                PrefManager.class,
                Bus.class,
                Jobber.class
        }
)
public class BaseAppModule {

    public static Application s_application = null;

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link com.f3rog.alf.dagger.qualifier.ForApplication} annotation to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return s_application.getApplicationContext();
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return s_application;
    }

    /**
     * This should be used only by {@link Bus} class
     */
    @Provides
    @Singleton
    EventBus provideBus() {
        return EventBus.getDefault();
    }

    /**
     * This should be used only by {@link Jobber} class
     */
    @Provides
    @Singleton
    JobManager provideJobManager(@ForApplication Context context) {
        return new JobManager(context);
    }

}