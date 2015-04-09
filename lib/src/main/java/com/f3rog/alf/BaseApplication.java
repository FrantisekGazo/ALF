package com.f3rog.alf;

import android.app.Application;

import com.f3rog.alf.dagger.BaseAppModule;
import com.f3rog.alf.dagger.Injector;

import java.util.ArrayList;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Base Application injected with Dagger.
 *
 * @author f3rog
 */
public abstract class BaseApplication extends Application implements Injector {

    private ObjectGraph _objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        BaseAppModule sharedModule = new BaseAppModule();
        BaseAppModule.s_application = this;

        List<Object> modules = new ArrayList<Object>();
        modules.add(sharedModule);
        modules.addAll(getAppModules());

        _objectGraph = ObjectGraph.create(modules.toArray());
        _objectGraph.inject(this);
        _objectGraph.injectStatics();
    }

    @Override
    public void inject(Object object) {
        _objectGraph.inject(object);
    }

    @Override
    public ObjectGraph getObjectGraph() {
        return _objectGraph;
    }

    protected abstract List<Object> getAppModules();

}