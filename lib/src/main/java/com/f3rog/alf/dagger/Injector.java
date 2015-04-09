package com.f3rog.alf.dagger;

import dagger.ObjectGraph;

/**
 * Class {@link Injector} is capable of injecting dependencies.
 *
 * @author f3rog
 */
public interface Injector {

    /**
     * Inject to object
     *
     * @param object Object
     */
    public void inject(Object object);

    public ObjectGraph getObjectGraph();
}