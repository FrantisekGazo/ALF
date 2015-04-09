package com.f3rog.alf;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.f3rog.alf.dagger.Injector;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 * Base Activity which performs injection using the activity-scoped object graph
 * and also inject views with ButterKnife.
 *
 * @author f3rog
 */
public abstract class BaseActivity extends ActionBarActivity implements Injector {

    private ObjectGraph _activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Create the activity scoped graph by .plus-ing our modules onto the application graph.
        BaseApplication application = (BaseApplication) getApplication();
        _activityGraph = application.getObjectGraph().plus(geActivityModules());
        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        _activityGraph.inject(this);

        // super.onCreate() creates all fragments => call it after graph was initialized
        super.onCreate(savedInstanceState);
        // inject views
        setContentView(getLayout());
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as soon as possible.
        _activityGraph = null;
        super.onDestroy();
    }

    protected <T> T getView(int id) {
        return (T) findViewById(id);
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    @Override
    public void inject(Object object) {
        _activityGraph.inject(object);
    }

    public ObjectGraph getObjectGraph() {
        return _activityGraph;
    }

    protected abstract Object[] geActivityModules();

    protected abstract int getLayout();

}